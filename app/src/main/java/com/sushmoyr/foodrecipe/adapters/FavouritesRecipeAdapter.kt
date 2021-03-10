package com.sushmoyr.foodrecipe.adapters

import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.data.database.entities.FavouritesEntity
import com.sushmoyr.foodrecipe.databinding.FavouritesRowLayoutBinding
import com.sushmoyr.foodrecipe.ui.fragments.FavouriteFragmentDirections
import com.sushmoyr.foodrecipe.util.RecipesDiffUtil
import com.sushmoyr.foodrecipe.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.favourites_row_layout.view.*

class FavouritesRecipeAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<FavouritesRecipeAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favouriteRecipes = emptyList<FavouritesEntity>()

    private var multiSelection: Boolean = false
    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var viewHolders = arrayListOf<MyViewHolder>()
    private lateinit var actionMode: ActionMode
    private lateinit var rootView: View

    class MyViewHolder(private val binding: FavouritesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouritesEntity = favouritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavouritesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = favouriteRecipes[position]
        rootView = holder.itemView
        holder.bind(currentRecipe)

        holder.itemView.favourites_recipes_row_layout_root.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailsActivity(
                    currentRecipe.result
                )
                holder.itemView.findNavController().navigate(action)
            }
        }

        holder.itemView.favourites_recipes_row_layout_root.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }

        }

    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavouritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            viewHolders.remove(holder)
            changeRecipeStyle(holder, R.color.card_background_color, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            viewHolders.add(holder)
            changeRecipeStyle(holder, R.color.cardBackgroundLight, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.favourites_recipes_row_layout_root.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.favouritesCardView.strokeColor =
            ContextCompat.getColor(requireActivity, backgroundColor)
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }

    fun setData(newFavouriteRecipes: List<FavouritesEntity>) {
        val favouritesRecipesDiffUtil = RecipesDiffUtil(favouriteRecipes, newFavouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favouritesRecipesDiffUtil)
        favouriteRecipes = newFavouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
        Log.d("favourites", "SetData called")
        Log.d("favourites", "data size = $itemCount")
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                actionMode.finish()
            }
            1 -> {
                actionMode.title = "1 item selected"
            }
            else -> {
                actionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }


    //Action Mode

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        actionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favourite_recipe) {
            selectedRecipes.forEach { recipe ->
                mainViewModel.deleteFavouriteRecipes(recipe)
            }
            showSnackbar("${selectedRecipes.size} item(s) removed")

            multiSelection = false
            selectedRecipes.clear()
            actionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        multiSelection = false
        selectedRecipes.clear()
        viewHolders.forEach { item ->
            changeRecipeStyle(item, R.color.card_background_color, R.color.strokeColor)
        }
        viewHolders.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun clearContextualActionMode() {
        if (this::actionMode.isInitialized) {
            actionMode.finish()
        }
    }
}
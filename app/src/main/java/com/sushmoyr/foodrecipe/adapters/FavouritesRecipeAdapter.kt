package com.sushmoyr.foodrecipe.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sushmoyr.foodrecipe.data.database.entities.FavouritesEntity
import com.sushmoyr.foodrecipe.databinding.FavouritesRowLayoutBinding
import com.sushmoyr.foodrecipe.ui.fragments.FavouriteFragmentDirections
import com.sushmoyr.foodrecipe.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.favourites_row_layout.view.*

class FavouritesRecipeAdapter: RecyclerView.Adapter<FavouritesRecipeAdapter.MyViewHolder>() {

    private var favouriteRecipes = emptyList<FavouritesEntity>()

    class MyViewHolder(private val binding: FavouritesRowLayoutBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(favouritesEntity: FavouritesEntity){
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
        val selectedRecipe = favouriteRecipes[position]
        holder.bind(selectedRecipe)

        holder.itemView.favourites_recipes_row_layout_root.setOnClickListener {
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailsActivity(
                selectedRecipe.result
            )
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }

    fun setData(newFavouriteRecipes: List<FavouritesEntity>){
        val favouritesRecipesDiffUtil = RecipesDiffUtil(favouriteRecipes, newFavouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favouritesRecipesDiffUtil)
        favouriteRecipes = newFavouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
        Log.d("favourites","SetData called")
        Log.d("favourites","data size = $itemCount")
    }
}
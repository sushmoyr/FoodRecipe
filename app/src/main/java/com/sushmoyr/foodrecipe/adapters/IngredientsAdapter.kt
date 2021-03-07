package com.sushmoyr.foodrecipe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.models.ExtendedIngredient
import com.sushmoyr.foodrecipe.util.Constants.Companion.BASE_IMAGE_URL
import com.sushmoyr.foodrecipe.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredients_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image)
        {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.itemView.title_ingredient.text = ingredientsList[position].name.capitalize(Locale.ROOT)
        holder.itemView.ingredient_ammount.text = ingredientsList[position].amount.toString()
        holder.itemView.ingredient_unit.text = ingredientsList[position].unit.toString()
        holder.itemView.ingredient_consistency.text = ingredientsList[position].consistency
        holder.itemView.ingredient_original.text = ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>){
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
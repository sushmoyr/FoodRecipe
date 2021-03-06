package com.sushmoyr.foodrecipe.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sushmoyr.foodrecipe.data.database.RecipesEntity
import com.sushmoyr.foodrecipe.models.FoodRecipe
import com.sushmoyr.foodrecipe.util.NetworkResult

class FragmentRecipesBindingAdapter {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorViewVisibility(
            view: View,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                view.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading) {
                view.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Success) {
                view.visibility = View.INVISIBLE
            }
            when (view) {
                is TextView -> {
                    if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                        view.text = apiResponse.message.toString()
                    }
                }
            }


        }
    }
}
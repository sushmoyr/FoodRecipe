package com.sushmoyr.foodrecipe.adapters.bindingAdapters

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.sushmoyr.foodrecipe.data.database.entities.FoodJokeEntity
import com.sushmoyr.foodrecipe.models.FoodJoke
import com.sushmoyr.foodrecipe.util.NetworkResult

class FoodJokeBinding {

    companion object {

        @BindingAdapter("setVisibilityApiResponse", "setVisibilityDatabase", requireAll = false)
        @JvmStatic
        fun setViewVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null) {
                                if (database.isEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

    }
}
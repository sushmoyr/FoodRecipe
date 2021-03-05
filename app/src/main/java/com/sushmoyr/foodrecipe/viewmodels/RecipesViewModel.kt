package com.sushmoyr.foodrecipe.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sushmoyr.foodrecipe.util.Constants.Companion.API_KEY
import com.sushmoyr.foodrecipe.util.Constants.Companion.BASE_URL
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_API_KEY
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_DIET
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_NUMBER
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_TYPE


class RecipesViewModel(application: Application) : AndroidViewModel(application) {
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "20"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "main course"
        queries[QUERY_DIET] = "paleo"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}
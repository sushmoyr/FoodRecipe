package com.sushmoyr.foodrecipe.data.network

import android.util.Log
import com.sushmoyr.foodrecipe.data.network.FoodRecipesApi
import com.sushmoyr.foodrecipe.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
)
{
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        val entryset = queries.entries
        for (i in entryset)
        {
            Log.d("latest", "${i.key}  ->  ${i.value}")
        }
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        val entryset = searchQuery.entries
        for (i in entryset)
        {
            Log.d("latest", "${i.key}  ->  ${i.value}")
        }
        return foodRecipesApi.searchRecipes(searchQuery)
    }

}
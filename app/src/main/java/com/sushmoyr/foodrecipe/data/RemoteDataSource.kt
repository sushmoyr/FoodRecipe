package com.sushmoyr.foodrecipe.data

import com.sushmoyr.foodrecipe.data.network.FoodRecipesApi
import com.sushmoyr.foodrecipe.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
}
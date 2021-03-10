package com.sushmoyr.foodrecipe.data

import com.sushmoyr.foodrecipe.data.database.RecipesDao
import com.sushmoyr.foodrecipe.data.database.entities.FavouritesEntity
import com.sushmoyr.foodrecipe.data.database.entities.FoodJokeEntity
import com.sushmoyr.foodrecipe.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    fun readFavouriteRecipes(): Flow<List<FavouritesEntity>>{
        return recipesDao.readFavouriteRecipe()
    }

    suspend fun insertFavouriteRecipes(favouritesEntity: FavouritesEntity){
        recipesDao.insertFavouriteRecipe(favouritesEntity)
    }

    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity) {
        recipesDao.deleteFavouriteRecipes(favouritesEntity)
    }

    suspend fun deleteAllFavouriteRecipes() {
        recipesDao.deleteAllFavouriteRecipes()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }


}
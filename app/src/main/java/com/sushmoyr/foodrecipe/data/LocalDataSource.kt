package com.sushmoyr.foodrecipe.data

import com.sushmoyr.foodrecipe.data.database.RecipesDao
import com.sushmoyr.foodrecipe.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readDatabase(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }
}
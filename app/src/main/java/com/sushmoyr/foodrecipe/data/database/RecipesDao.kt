package com.sushmoyr.foodrecipe.data.database

import androidx.room.*
import com.sushmoyr.foodrecipe.data.database.entities.FavouritesEntity
import com.sushmoyr.foodrecipe.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("SELECT * FROM favourites_recipes_table ORDER BY id ASC")
    fun readFavouriteRecipe():Flow<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFavouriteRecipes(favouritesEntity: FavouritesEntity)

    @Query("DELETE FROM favourites_recipes_table")
    suspend fun deleteAllFavouriteRecipes()
}
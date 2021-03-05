package com.sushmoyr.foodrecipe.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sushmoyr.foodrecipe.models.FoodRecipe
import com.sushmoyr.foodrecipe.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe) {
    @PrimaryKey(autoGenerate = false)
    var id = 0
}
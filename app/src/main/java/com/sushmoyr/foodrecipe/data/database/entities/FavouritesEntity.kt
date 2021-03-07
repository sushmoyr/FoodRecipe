package com.sushmoyr.foodrecipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sushmoyr.foodrecipe.models.Result
import com.sushmoyr.foodrecipe.util.Constants.Companion.FAVOURITES_RECIPES_TABLE

@Entity(tableName = FAVOURITES_RECIPES_TABLE)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)
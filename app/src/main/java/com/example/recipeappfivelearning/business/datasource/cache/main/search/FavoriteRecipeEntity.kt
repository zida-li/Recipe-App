package com.example.recipeappfivelearning.business.datasource.cache.main.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteRecipes")
data class FavoriteRecipeEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "label")
    var recipeName: String,

    @ColumnInfo(name = "image")
    var recipeImageUrl: String,

    @ColumnInfo(name = "ingredientLines")
    var recipeIngredients: String,

    @ColumnInfo(name = "uri")
    var recipeId: String,

    @ColumnInfo(name = "calories")
    var recipeCalories: Float,

    @ColumnInfo(name = "timeSaved")
    var timeSaved: String? = null,

)
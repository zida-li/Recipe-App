package com.example.recipeappfivelearning.business.datasource.cache.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Converters

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

fun Recipe.toFavoriteEntity(): FavoriteRecipeEntity {
    return FavoriteRecipeEntity(
        recipeName = recipeName!!,
        recipeImageUrl = recipeImageUrl!!,
        recipeIngredients = Converters.convertIngredientListToString(recipeIngredients!!),
        recipeId = recipeId!!,
        recipeCalories = recipeCalories!!,
        timeSaved = timeSaved,
    )
}

fun FavoriteRecipeEntity.toFavoriteRecipe(): Recipe {
    return Recipe(
        recipeName = recipeName!!,
        recipeImageUrl = recipeImageUrl!!,
        recipeIngredients = Converters.convertIngredientsToList(recipeIngredients),
        recipeId = recipeId!!,
        recipeCalories = recipeCalories!!,
        timeSaved = timeSaved,
    )
}
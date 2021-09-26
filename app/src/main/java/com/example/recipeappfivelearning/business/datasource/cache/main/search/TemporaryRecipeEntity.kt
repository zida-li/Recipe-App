package com.example.recipeappfivelearning.business.datasource.cache.main.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Converters

@Entity(tableName = "temporaryRecipe")
data class TemporaryRecipeEntity (

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

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean

)

fun Recipe.toEntity(): TemporaryRecipeEntity {
    return TemporaryRecipeEntity(
        recipeName = recipeName!!,
        recipeImageUrl = recipeImageUrl!!,
        recipeIngredients = Converters.convertIngredientListToString(recipeIngredients!!),
        recipeId = recipeId!!,
        recipeCalories = recipeCalories!!,
        isFavorite = isFavorite,
    )
}

fun TemporaryRecipeEntity.toRecipe(): Recipe{
    return Recipe(
        recipeName = recipeName,
        recipeImageUrl = recipeImageUrl,
        recipeIngredients = Converters.convertIngredientsToList(recipeIngredients),
        recipeId = recipeId,
        recipeCalories = recipeCalories,
        isFavorite = isFavorite,
    )
}
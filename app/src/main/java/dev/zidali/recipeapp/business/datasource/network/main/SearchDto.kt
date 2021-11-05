package dev.zidali.recipeapp.business.datasource.network.main

import com.google.gson.annotations.SerializedName
import dev.zidali.recipeapp.business.domain.models.Recipe

data class SearchDto (

    @SerializedName("recipe")
    var searchDto: SearchDtoModel

)

data class SearchDtoModel(

    @SerializedName("label")
    var recipeName: String,

    @SerializedName("image")
    var recipeImageUrl: String,

    @SerializedName("ingredientLines")
    var recipeIngredients: List<String> = listOf(),

    @SerializedName("uri")
    var recipeId: String,

    @SerializedName("calories")
    var recipeCalories: Float,

)

fun SearchDto.toSearchStateRecipeModel(): Recipe {
    return Recipe(
        recipeName = searchDto.recipeName,
        recipeImageUrl = searchDto.recipeImageUrl,
        recipeIngredients = searchDto.recipeIngredients,
        recipeId = searchDto.recipeId,
        recipeCalories = searchDto.recipeCalories,
    )
}
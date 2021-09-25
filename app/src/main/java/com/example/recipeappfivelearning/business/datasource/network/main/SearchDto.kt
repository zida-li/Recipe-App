package com.example.recipeappfivelearning.business.datasource.network.main

import com.example.recipeappfivelearning.presentation.main.search.list.SearchState
import com.google.gson.annotations.SerializedName

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

fun SearchDto.toSearchStateRecipeModel(): SearchState.SearchStateRecipeModel {
    return SearchState.SearchStateRecipeModel(
        recipeName = searchDto.recipeName,
        recipeImageUrl = searchDto.recipeImageUrl,
        recipeIngredients = searchDto.recipeIngredients,
        recipeId = searchDto.recipeId,
        recipeCalories = searchDto.recipeCalories,
    )
}
package com.example.recipeappfivelearning.presentation.main.search.detail

sealed class ViewRecipeEvents{

    data class FetchSearchRecipe(
        val recipeName: String
    ): ViewRecipeEvents()

}
package com.example.recipeappfivelearning.presentation.main.favorite.detail

sealed class FavoriteRecipeEvents{

    data class FetchFavoriteRecipe(
        val recipeName: String
    ): FavoriteRecipeEvents()

    object DeleteRecipe: FavoriteRecipeEvents()

}
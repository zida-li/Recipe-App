package com.example.recipeappfivelearning.presentation.main.favorite.detail

import com.example.recipeappfivelearning.business.domain.models.Recipe

sealed class FavoriteRecipeEvents{

    data class FetchFavoriteRecipe(
        val recipeName: String
    ): FavoriteRecipeEvents()

    object DeleteRecipe: FavoriteRecipeEvents()

    object AddToShoppingList: FavoriteRecipeEvents()

    object CompareFavoriteToShoppingList: FavoriteRecipeEvents()

    object DeleteRecipeFromShoppingList: FavoriteRecipeEvents()

}
package com.example.recipeappfivelearning.presentation.main.favorite.list

import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.StateMessage

sealed class FavoriteEvents {

    object FetchFavoriteRecipes: FavoriteEvents()

    data class AddOrRemoveRecipeFromSelectedList(
        val recipe: Recipe
    ): FavoriteEvents()

    object ClearSelectedRecipes: FavoriteEvents()

    object DeleteSelectedRecipes: FavoriteEvents()

    data class SetToolBarState (
        val favoriteListToolbarState: FavoriteListToolbarState
    ): FavoriteEvents()

    data class Error(val stateMessage: StateMessage): FavoriteEvents()

}
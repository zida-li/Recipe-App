package com.example.recipeappfivelearning.presentation.main.favorite.list

import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.StateMessage

sealed class FavoriteEvents {

    /**
     * Fetch Recipes From Database
     */

    object FetchFavoriteRecipes: FavoriteEvents()

    /**
     * MultiSelectionMode
     */

    data class SetToolBarState (
        val favoriteListToolbarState: FavoriteListToolbarState
    ): FavoriteEvents()

    data class AddOrRemoveRecipeFromSelectedList(
        val recipe: Recipe
    ): FavoriteEvents()

    object ClearSelectedRecipes: FavoriteEvents()

    object DeleteSelectedRecipes: FavoriteEvents()

    /**
     * Alert Dialogs
     */

    data class AppendToMessageQueue(val stateMessage: StateMessage): FavoriteEvents()

    object OnRemoveHeadFromQueue: FavoriteEvents()

}
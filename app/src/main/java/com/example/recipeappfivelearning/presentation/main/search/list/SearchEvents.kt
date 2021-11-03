package com.example.recipeappfivelearning.presentation.main.search.list

import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.StateMessage

sealed class SearchEvents{

    /**
     * Search
     */

    object NewSearch: SearchEvents()

    object NextPage: SearchEvents()

    data class UpdateQuery(
        val query: String
    ): SearchEvents()

    object CompareSearchToFavorite: SearchEvents()

    /**
     * Save/Delete To Database
     */

    data class SaveOrDeleteRecipeFromDb(
        val recipe: Recipe
    ): SearchEvents()

    data class SaveRecipe(
        val recipe: Recipe
    ): SearchEvents()

    data class DeleteRecipe(
        val recipe: Recipe
    ): SearchEvents()

    data class SaveToTemporaryRecipeDb(
        val recipe: Recipe
    ): SearchEvents()

    /**
     * Alert Dialogs
     */

    data class AppendToMessageQueue(val stateMessage: StateMessage): SearchEvents()

    object OnRemoveHeadFromQueue: SearchEvents()

}

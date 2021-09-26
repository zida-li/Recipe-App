package com.example.recipeappfivelearning.presentation.main.search.list

import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.StateMessage

sealed class SearchEvents{

    object NewSearch: SearchEvents()

    data class UpdateQuery(
        val query: String
    ): SearchEvents()

    object NextPage: SearchEvents()

    data class SaveToTemporaryRecipeDb(
        val recipe: Recipe
    ): SearchEvents()

    object OnRemoveHeadFromQueue: SearchEvents()

    data class Error(val stateMessage: StateMessage): SearchEvents()

    data class SaveOrDeleteRecipeFromDb(
        val recipe: Recipe
    ): SearchEvents()

}

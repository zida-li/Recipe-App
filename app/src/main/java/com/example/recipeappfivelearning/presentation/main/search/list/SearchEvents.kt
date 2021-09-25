package com.example.recipeappfivelearning.presentation.main.search.list

import com.example.recipeappfivelearning.business.domain.util.StateMessage

sealed class SearchEvents{

    object NewSearch: SearchEvents()

    data class UpdateQuery(
        val query: String
    ): SearchEvents()

    object NextPage: SearchEvents()

    object SaveToTemporaryRecipeDb: SearchEvents()

    object OnRemoveHeadFromQueue: SearchEvents()

    data class Error(val stateMessage: StateMessage): SearchEvents()

}

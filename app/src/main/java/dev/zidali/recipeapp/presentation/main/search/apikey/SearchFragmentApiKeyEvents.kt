package dev.zidali.recipeapp.presentation.main.search.apikey

import dev.zidali.recipeapp.business.domain.util.StateMessage

sealed class SearchFragmentApiKeyEvents {

    object FetchApiKey: SearchFragmentApiKeyEvents()

    data class SetApiKey(
        val app_id: String,
        val app_key: String,
    ): SearchFragmentApiKeyEvents()

    data class OnUpdateAppId(
        val app_id: String
    ): SearchFragmentApiKeyEvents()

    data class OnUpdateAppKey(
        val app_key: String
    ): SearchFragmentApiKeyEvents()

    data class AppendToMessageQueue(val stateMessage: StateMessage): SearchFragmentApiKeyEvents()

    object OnRemoveHeadFromQueue: SearchFragmentApiKeyEvents()

}
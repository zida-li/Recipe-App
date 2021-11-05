package dev.zidali.recipeapp.presentation.main.apikey

import dev.zidali.recipeapp.business.domain.util.StateMessage

sealed class ApiKeyEvents {

    object FetchApiKey: ApiKeyEvents()

    data class SetApiKey(
        val app_id: String,
        val app_key: String,
    ): ApiKeyEvents()

    data class OnUpdateAppId(
        val app_id: String
    ): ApiKeyEvents()

    data class OnUpdateAppKey(
        val app_key: String
    ): ApiKeyEvents()

    data class AppendToMessageQueue(val stateMessage: StateMessage): ApiKeyEvents()

    object OnRemoveHeadFromQueue: ApiKeyEvents()

}
package dev.zidali.recipeapp.presentation.main.apikey

import dev.zidali.recipeapp.business.domain.models.ApiKey
import dev.zidali.recipeapp.business.domain.util.Queue
import dev.zidali.recipeapp.business.domain.util.StateMessage

data class ApiKeyState (
    var app_id: String = "",
    var app_key: String = "",
    var apikey: ApiKey? = null,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
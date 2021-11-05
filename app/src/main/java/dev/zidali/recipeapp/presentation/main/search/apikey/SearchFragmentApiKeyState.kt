package dev.zidali.recipeapp.presentation.main.search.apikey

import dev.zidali.recipeapp.business.domain.util.Queue
import dev.zidali.recipeapp.business.domain.util.StateMessage

data class SearchFragmentApiKeyState (
    var app_id: String = "",
    var app_key: String = "",
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
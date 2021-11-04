package us.zidali.recipeapp.presentation.main.search.apikey

import us.zidali.recipeapp.business.domain.util.Queue
import us.zidali.recipeapp.business.domain.util.StateMessage

data class SearchFragmentApiKeyState (
    var app_id: String = "",
    var app_key: String = "",
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
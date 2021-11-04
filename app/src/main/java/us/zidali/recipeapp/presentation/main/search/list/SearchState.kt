package us.zidali.recipeapp.presentation.main.search.list

import us.zidali.recipeapp.business.domain.models.ApiKey
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.Queue
import us.zidali.recipeapp.business.domain.util.StateMessage

data class SearchState (
    var recipeList: MutableList<Recipe> = mutableListOf(),
    var recipe: Recipe? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    var moreResultAvailable: Boolean? = null,
    var page: Int? = 1,
    var pageSize: Int = 10,
    var fromPage: Int? = 0,
    var toPage: Int? = 10,
    var apiKey: ApiKey? = null,
    var dataLoading: Boolean = true,
    var navigateToApiFragment: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
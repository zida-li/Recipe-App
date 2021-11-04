package us.zidali.recipeapp.presentation.main.favorite.list

import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.Queue
import us.zidali.recipeapp.business.domain.util.StateMessage

data class FavoriteState (
    var recipeList: MutableList<Recipe> = mutableListOf(),
    var recipe: Recipe? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
package us.zidali.recipeapp.presentation.main.search.detail

import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.Queue
import us.zidali.recipeapp.business.domain.util.StateMessage

data class ViewRecipeState (
    var recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
package us.zidali.recipeapp.presentation.main.favorite.detail

import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.Queue
import us.zidali.recipeapp.business.domain.util.StateMessage

data class FavoriteRecipeState (
    var recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
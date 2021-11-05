package dev.zidali.recipeapp.presentation.main.favorite.list

import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.Queue
import dev.zidali.recipeapp.business.domain.util.StateMessage

data class FavoriteState (
    var recipeList: MutableList<Recipe> = mutableListOf(),
    var recipe: Recipe? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
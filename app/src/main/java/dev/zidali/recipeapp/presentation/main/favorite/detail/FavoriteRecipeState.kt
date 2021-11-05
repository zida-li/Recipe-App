package dev.zidali.recipeapp.presentation.main.favorite.detail

import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.Queue
import dev.zidali.recipeapp.business.domain.util.StateMessage

data class FavoriteRecipeState (
    var recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
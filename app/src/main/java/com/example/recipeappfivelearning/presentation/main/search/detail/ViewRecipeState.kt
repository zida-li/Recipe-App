package com.example.recipeappfivelearning.presentation.main.search.detail

import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Queue
import com.example.recipeappfivelearning.business.domain.util.StateMessage

data class ViewRecipeState (
    var recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
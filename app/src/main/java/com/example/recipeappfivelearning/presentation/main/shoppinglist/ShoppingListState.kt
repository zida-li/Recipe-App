package com.example.recipeappfivelearning.presentation.main.shoppinglist

import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Queue
import com.example.recipeappfivelearning.business.domain.util.StateMessage

data class ShoppingListState (

    var recipeList: MutableList<Recipe> = mutableListOf(),
    var recipe: Recipe? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    var initialListSize: Int? = null,
    var needToReload: Boolean = false,
    var scrollPosition: Int = 0,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),

)
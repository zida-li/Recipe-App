package dev.zidali.recipeapp.presentation.main.shoppinglist

import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.Queue
import dev.zidali.recipeapp.business.domain.util.StateMessage

data class ShoppingListState (

    var recipeList: MutableList<Recipe> = mutableListOf(),
    var recipe: Recipe? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    var initialListSize: Int? = null,
    var dataLoading: Boolean = false,
    var scrollPosition: Int = 0,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),

    )
package com.example.recipeappfivelearning.presentation.main.search.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.interactors.main.search.detail.FetchSearchRecipe
import com.example.recipeappfivelearning.presentation.main.search.detail.ViewRecipeEvents
import com.example.recipeappfivelearning.presentation.main.search.detail.ViewRecipeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ViewRecipeViewModel
@Inject
constructor(
    private val fetchSearchRecipe: FetchSearchRecipe,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){

    init {
        savedStateHandle.get<String>("recipeName")?.let {recipeName ->
            onTriggerEvent(ViewRecipeEvents.FetchSearchRecipe(recipeName))
        }
    }

    val state: MutableLiveData<ViewRecipeState> = MutableLiveData(ViewRecipeState())

    fun onTriggerEvent(event: ViewRecipeEvents) {
        when(event) {
            is ViewRecipeEvents.FetchSearchRecipe -> {
                fetchSearchRecipe(event.recipeName)
            }
        }
    }

    private fun fetchSearchRecipe(recipeName: String) {
        fetchSearchRecipe.execute(
            recipeName = recipeName
        ).onEach { dataState ->

            dataState.data?.recipe.let {
                state.value = state.value?.copy(recipe = it)
            }

        }.launchIn(viewModelScope)
    }

}
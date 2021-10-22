package com.example.recipeappfivelearning.presentation.main.favorite.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.interactors.main.shared.DeleteRecipeFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.shared.DeleteRecipeFromShoppingList
import com.example.recipeappfivelearning.business.interactors.main.shared.CompareToShoppingList
import com.example.recipeappfivelearning.business.interactors.main.shared.AddToShoppingList
import com.example.recipeappfivelearning.business.interactors.main.favorite.detail.FetchFavoriteRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel
@Inject
constructor(
    private val fetchFavoriteRecipe: FetchFavoriteRecipe,
    private val savedStateHandle: SavedStateHandle,
    private val deleteRecipeFromFavorite: DeleteRecipeFromFavorite,
    private val addToShoppingList: AddToShoppingList,
    private val compareToShoppingList: CompareToShoppingList,
    private val deleteRecipeFromShoppingList: DeleteRecipeFromShoppingList,
): ViewModel(){

    init {
        savedStateHandle.get<String>("favoriteRecipeName")?.let {recipeName ->
            onTriggerEvent(FavoriteRecipeEvents.FetchFavoriteRecipe(recipeName))
        }
    }

    val state: MutableLiveData<FavoriteRecipeState> = MutableLiveData(FavoriteRecipeState())

    fun onTriggerEvent(event: FavoriteRecipeEvents) {
        when(event) {
            is FavoriteRecipeEvents.FetchFavoriteRecipe -> {
                fetchFavoriteRecipe(event.recipeName)
            }
            is FavoriteRecipeEvents.DeleteRecipe -> {
                deleteRecipe()
            }
            is FavoriteRecipeEvents.AddToShoppingList -> {
                addToShoppingList()
            }
            is FavoriteRecipeEvents.CompareFavoriteToShoppingList -> {
                compareFavoriteToShoppingList()
            }
            is FavoriteRecipeEvents.DeleteRecipeFromShoppingList -> {
                deleteRecipeFromShoppingList()
            }
        }
    }

    private fun addToShoppingList() {
        addToShoppingList.execute(state.value?.recipe!!).launchIn(viewModelScope)
    }

    private fun deleteRecipe() {
        state.value?.let { state->
            state.recipe?.let { recipe->
                deleteRecipeFromFavorite.execute(
                    recipe
                ).launchIn(viewModelScope)
            }
        }
    }

    private fun fetchFavoriteRecipe(recipeName: String) {
        fetchFavoriteRecipe.execute(
            recipeName = recipeName
        ).onEach { dataState ->

            dataState.data?.let {
                state.value = state.value?.copy(recipe = it)
                compareFavoriteToShoppingList()
//                Log.d("AppDebug", "fetchFavoriteRecipe: ${it.isInShoppingList}")
            }

        }.launchIn(viewModelScope)
    }

    private fun compareFavoriteToShoppingList() {
        compareToShoppingList.execute(
            state.value?.recipe!!
        ).onEach {dataState ->

            dataState.data?.let {
                state.value = state.value?.copy(recipe = it)
            }

        }.launchIn(viewModelScope)
    }

    private fun deleteRecipeFromShoppingList(){
        deleteRecipeFromShoppingList.execute(
            state.value?.recipe!!
        ).launchIn(viewModelScope)
    }

}
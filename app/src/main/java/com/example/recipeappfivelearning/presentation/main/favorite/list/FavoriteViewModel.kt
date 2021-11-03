package com.example.recipeappfivelearning.presentation.main.favorite.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.StateMessage
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import com.example.recipeappfivelearning.business.domain.util.doesMessageAlreadyExistInQueue
import com.example.recipeappfivelearning.business.interactors.main.favorite.detail.DeleteMultipleRecipesFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.favorite.list.FetchFavoriteRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(
    private val fetchFavoriteRecipes: FetchFavoriteRecipes,
    private val deleteMultipleRecipesFromFavorite: DeleteMultipleRecipesFromFavorite,
) : ViewModel() {

    private val TAG: String = "AppDebug"

    val favoriteListInteractionManager = FavoriteListInteractionManager()

    val toolbarState: LiveData<FavoriteListToolbarState>
        get() = favoriteListInteractionManager.toolbarState

    val state: MutableLiveData<FavoriteState> = MutableLiveData(FavoriteState())

    fun onTriggerEvent(event: FavoriteEvents) {
        when(event) {
            is FavoriteEvents.FetchFavoriteRecipes -> {
                fetchFavoriteRecipes()
            }
            is FavoriteEvents.SetToolBarState -> {
                setToolBarState(event.favoriteListToolbarState)
            }
            is FavoriteEvents.AddOrRemoveRecipeFromSelectedList -> {
                addOrRemoveRecipeFromSelectedList(event.recipe)
            }
            is FavoriteEvents.ClearSelectedRecipes -> {
                clearSelectedRecipes()
            }
            is FavoriteEvents.DeleteSelectedRecipes -> {
                deleteSelectedRecipes()
            }
            is FavoriteEvents.AppendToMessageQueue -> {
                appendToMessageQueue(event.stateMessage)
            }
            is FavoriteEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }

    /**
     * Fetch Recipes From Database
     */

    private fun fetchFavoriteRecipes() {
        state.value?.let {state->
            fetchFavoriteRecipes.execute().onEach {dataState ->

                dataState.data?.let { list->
                    this.state.value = state.copy(recipeList = list)
                }

            }.launchIn(viewModelScope)
        }
    }

    /**
     * MultiSelectionMode
     */

    private fun setToolBarState(favoriteListToolbarState: FavoriteListToolbarState) {
        favoriteListInteractionManager.setToolbarState(favoriteListToolbarState)
    }

    private fun addOrRemoveRecipeFromSelectedList(recipe: Recipe) {
        favoriteListInteractionManager.addOrRemoveRecipeFromSelectedList(recipe)
    }

    private fun clearSelectedRecipes() {
        favoriteListInteractionManager.clearSelectedRecipes()
    }

    private fun deleteSelectedRecipes() {
        if(getSelectedRecipes().size > 0) {
            deleteMultipleRecipesFromFavorite.execute(getSelectedRecipes()).launchIn(viewModelScope)
            removeSelectedRecipesFromList()
        }
    }

    /**
     * Alert Dialogs
     */

    private fun removeHeadFromQueue() {
        state.value?.let { state ->
            try {
                val queue = state.queue
                queue.remove() // can throw exception if empty
                this.state.value = state.copy(queue = queue)
            } catch (e: Exception) {
                Log.d(TAG, "removeHeadFromQueue: Nothing to remove from DialogQueue")
            }
        }
    }

    private fun appendToMessageQueue(stateMessage: StateMessage) {
        state.value?.let { state->
            val queue = state.queue
            if(!(stateMessage.doesMessageAlreadyExistInQueue(queue = queue))){
                if(stateMessage.response.uiComponentType !is UIComponentType.None){
                    queue.add(stateMessage)
                    this.state.value = state.copy(queue = queue)
                }
            }
        }
    }

    /**
     * Supporting Functions
     */

    fun isRecipeSelected(recipe: Recipe): Boolean {
        return favoriteListInteractionManager.isRecipeSelected(recipe)
    }

    private fun getSelectedRecipes() = favoriteListInteractionManager.getSelectedRecipes()

    private fun removeSelectedRecipesFromList() {
        state.value?.recipeList?.removeAll(getSelectedRecipes())
        clearSelectedRecipes()
    }

}
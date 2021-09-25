package com.example.recipeappfivelearning.presentation.main.search.list.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.domain.util.Constants
import com.example.recipeappfivelearning.business.domain.util.StateMessage
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import com.example.recipeappfivelearning.business.domain.util.doesMessageAlreadyExistInQueue
import com.example.recipeappfivelearning.business.interactors.main.search.list.SearchRecipes
import com.example.recipeappfivelearning.presentation.main.search.list.SearchEvents
import com.example.recipeappfivelearning.presentation.main.search.list.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val searchRecipes: SearchRecipes
): ViewModel() {

    private val TAG: String = "AppDebug"

    val state: MutableLiveData<SearchState> = MutableLiveData(SearchState())

    fun onTriggerEvent(event: SearchEvents) {
        when(event) {
            is SearchEvents.NewSearch -> {
                newSearch()
            }
            is SearchEvents.UpdateQuery -> {
                onUpdateQuery(event.query)
            }
            is SearchEvents.NextPage -> {
                nextPage()
            }
            is SearchEvents.SaveToTemporaryRecipeDb -> {
                saveRecipeToTemporaryDb()
            }
            is SearchEvents.OnRemoveHeadFromQueue -> {
                onRemoveHeadFromQueue()
            }
            is SearchEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
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

    private fun onRemoveHeadFromQueue() {
        state.value?.let { state->
            try {
                val queue = state.queue
                queue.remove()
                this.state.value = state.copy(queue = queue)
            } catch (e: Exception) {
                Log.d(TAG, "onRemoveHeadFromQueue: Nothing to remove from DialogQueue")
            }
        }
    }

    private fun newSearch() {
        clearList()
        state.value?.let { state->
            searchRecipes.execute(
                app_key = Constants.app_key,
                app_id = Constants.app_id,
                query = state.query,
                from = 0,
                to = 10
            ).onEach { dataState ->

                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { searchState->
                    this.state.value = state.copy(recipeList = searchState.recipeList)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun saveRecipeToTemporaryDb() {

    }

    private fun nextPage() {
        TODO("Not yet implemented")
    }

}
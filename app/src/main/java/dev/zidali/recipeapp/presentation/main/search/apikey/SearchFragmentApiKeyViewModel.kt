package dev.zidali.recipeapp.presentation.main.search.apikey

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import dev.zidali.recipeapp.business.domain.models.ApiKey
import dev.zidali.recipeapp.business.domain.util.*
import dev.zidali.recipeapp.business.interactors.main.apikey.FetchApiKey
import dev.zidali.recipeapp.business.interactors.main.apikey.UpdateApiKey
import dev.zidali.recipeapp.presentation.main.apikey.ApiKeyState
import javax.inject.Inject

@HiltViewModel
class SearchFragmentApiKeyViewModel
@Inject
constructor(
    private val fetchApiKey: FetchApiKey,
    private val updateApiKey: UpdateApiKey,
) : ViewModel() {

    private val TAG: String = "AppDebug"

    val state: MutableLiveData<ApiKeyState> = MutableLiveData(ApiKeyState())

    fun onTriggerEvent(event: SearchFragmentApiKeyEvents) {
        when(event) {
            is SearchFragmentApiKeyEvents.FetchApiKey -> {
                fetchApiKey()
            }
            is SearchFragmentApiKeyEvents.SetApiKey -> {
                setApiKey(event.app_id, event.app_key)
            }
            is SearchFragmentApiKeyEvents.OnUpdateAppId -> {
                onUpdateAppId(event.app_id)
            }
            is SearchFragmentApiKeyEvents.OnUpdateAppKey -> {
                onUpdateAppKey(event.app_key)
            }
            is SearchFragmentApiKeyEvents.AppendToMessageQueue -> {
                appendToMessageQueue(event.stateMessage)
            }
            is SearchFragmentApiKeyEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }

    /**
     * Fetch/Set ApiKey From/To Database
     */

    private fun fetchApiKey() {

        state.value?.let {state->

            fetchApiKey.execute().onEach { dataState ->

                dataState.data?.let {
                    this.state.value = state.copy(apikey = it)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun setApiKey(app_id: String, app_key: String) {
        createApiKey(app_id, app_key)
        val apiKeyError = ApiKey(app_id, app_key).isValid()
        if(apiKeyError == ApiKey.ApiKeyError.none()) {
            updateApiKey.execute(state.value?.apikey!!).launchIn(viewModelScope)
        } else {
            appendToMessageQueue(
                stateMessage = StateMessage(
                    response = Response(
                        message = apiKeyError,
                        uiComponentType = UIComponentType.Dialog,
                        messageType = MessageType.Error
                    )
                )
            )
        }
    }

    /**
     * Cache states
     */

    private fun onUpdateAppId(app_id: String) {
        state.value?.let {state->
            this.state.value = state.copy(app_id = app_id)
        }
    }

    private fun onUpdateAppKey(app_key: String) {
        state.value?.let {state->
            this.state.value = state.copy(app_key = app_key)
        }
    }

    private fun createApiKey(app_id: String, app_key: String) {
        state.value?.apikey = ApiKey(
            app_id,
            app_key
        )
        state.value = state.value?.copy(apikey = ApiKey(app_id, app_key))
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
//                Log.d(TAG, "removeHeadFromQueue: Nothing to remove from DialogQueue")
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

}
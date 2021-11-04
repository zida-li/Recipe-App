package us.zidali.recipeapp.presentation.main.apikey

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import us.zidali.recipeapp.business.domain.models.ApiKey
import us.zidali.recipeapp.business.domain.util.*
import us.zidali.recipeapp.business.interactors.main.apikey.FetchApiKey
import us.zidali.recipeapp.business.interactors.main.apikey.UpdateApiKey
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel
@Inject
constructor(
    private val fetchApiKey: FetchApiKey,
    private val updateApiKey: UpdateApiKey,
) : ViewModel() {

    private val TAG: String = "AppDebug"

    val state: MutableLiveData<ApiKeyState> = MutableLiveData(ApiKeyState())

    fun onTriggerEvent(event: ApiKeyEvents) {
        when(event) {
            is ApiKeyEvents.FetchApiKey -> {
                fetchApiKey()
            }
            is ApiKeyEvents.SetApiKey -> {
                setApiKey(event.app_id, event.app_key)
            }
            is ApiKeyEvents.OnUpdateAppId -> {
                onUpdateAppId(event.app_id)
            }
            is ApiKeyEvents.OnUpdateAppKey -> {
                onUpdateAppKey(event.app_key)
            }
            is ApiKeyEvents.AppendToMessageQueue -> {
                appendToMessageQueue(event.stateMessage)
            }
            is ApiKeyEvents.OnRemoveHeadFromQueue -> {
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
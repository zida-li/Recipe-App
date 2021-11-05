package dev.zidali.recipeapp.business.interactors.main.apikey

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.apikey.ApiKeyDao
import dev.zidali.recipeapp.business.datasource.cache.main.apikey.toApiKeyEntity
import dev.zidali.recipeapp.business.domain.models.ApiKey
import dev.zidali.recipeapp.business.domain.util.DataState
import dev.zidali.recipeapp.business.domain.util.MessageType
import dev.zidali.recipeapp.business.domain.util.Response
import dev.zidali.recipeapp.business.domain.util.UIComponentType

class UpdateApiKey(
    private val apiKeyDao: ApiKeyDao,
) {

    fun execute(
        apiKey: ApiKey
    ): Flow<DataState<ApiKey>> = flow {

        try {
            apiKeyDao.deleteAllApiKey()
            apiKeyDao.insertApiKey(apiKey.toApiKeyEntity())
        } catch (e: Exception) {
            emit(
                DataState.error<ApiKey>(
                    response = Response(
                        message = "SaveRecipeToFavorite: saving was Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }

    }

}
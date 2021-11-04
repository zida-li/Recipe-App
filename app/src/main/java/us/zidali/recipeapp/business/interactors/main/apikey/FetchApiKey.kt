package us.zidali.recipeapp.business.interactors.main.apikey

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.apikey.ApiKeyDao
import us.zidali.recipeapp.business.datasource.cache.main.apikey.toApiKey
import us.zidali.recipeapp.business.datasource.network.handleUseCaseException
import us.zidali.recipeapp.business.domain.models.ApiKey
import us.zidali.recipeapp.business.domain.util.DataState

class FetchApiKey (
    private val apiKeyDao: ApiKeyDao,
) {

    fun execute(): Flow<DataState<ApiKey>> = flow {

        val cacheCall = apiKeyDao.getApiKey().toApiKey()

        emit(
            DataState.data(
                response = null,
                data = cacheCall
            ))

    }.catch { e->
        emit(handleUseCaseException(e))
    }

}
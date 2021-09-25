package com.example.recipeappfivelearning.business.interactors.main.search.list

import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.toEntity
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.presentation.main.search.list.SearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveRecipeToTemporaryRecipeDb (
    private val temporaryRecipeDao: TemporaryRecipeDao
) {

    fun execute(
        recipe: SearchState.SearchStateRecipeModel
    ): Flow<DataState<SearchState>> = flow {

        temporaryRecipeDao.insertRecipe(recipe.toEntity())

    }

}
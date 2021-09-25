package com.example.recipeappfivelearning.presentation.main.search.list.viewmodel

fun SearchViewModel.onUpdateQuery(query: String) {
    state.value = state.value?.copy(query = query)
}

fun SearchViewModel.clearList() {
    state.value?.let { state->
        this.state.value = state.copy(recipeList = mutableListOf())
    }
}
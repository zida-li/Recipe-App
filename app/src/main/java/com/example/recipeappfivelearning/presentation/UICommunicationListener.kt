package com.example.recipeappfivelearning.presentation

interface UICommunicationListener {

    fun displayProgressBar(isLoading: Boolean)

    fun expandAppBar()

    fun hideAppBar()

    fun hideSoftKeyboard()

}
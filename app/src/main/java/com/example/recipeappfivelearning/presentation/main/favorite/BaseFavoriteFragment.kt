package com.example.recipeappfivelearning.presentation.main.favorite

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.recipeappfivelearning.presentation.UICommunicationListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ClassCastException

@AndroidEntryPoint
abstract class BaseFavoriteFragment: Fragment() {

    val TAG: String = "AppDebug"

    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {

        }catch(e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }
    }
}
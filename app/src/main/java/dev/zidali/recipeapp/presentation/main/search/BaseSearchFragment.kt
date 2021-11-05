package dev.zidali.recipeapp.presentation.main.search

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.zidali.recipeapp.presentation.UICommunicationListener
import java.lang.ClassCastException

@AndroidEntryPoint
abstract class BaseSearchFragment : Fragment() {

    val TAG: String = "AppDebug"

    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        }catch(e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }
    }
}
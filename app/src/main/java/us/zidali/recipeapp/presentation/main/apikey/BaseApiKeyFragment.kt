package us.zidali.recipeapp.presentation.main.apikey

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import us.zidali.recipeapp.presentation.UICommunicationListener
import java.lang.ClassCastException

@AndroidEntryPoint
abstract class BaseApiKeyFragment: Fragment() {

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
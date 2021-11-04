package us.zidali.recipeapp.presentation.main.search.apikey

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import us.zidali.recipeapp.R
import us.zidali.recipeapp.business.domain.util.*
import us.zidali.recipeapp.databinding.FragmentApikeyBinding
import us.zidali.recipeapp.presentation.main.apikey.ApiKeyEvents
import us.zidali.recipeapp.presentation.util.processQueue

class SearchFragmentApiKeyFragment : BaseSearchFragmentApiKeyFragment()
{

    private val viewModel: SearchFragmentApiKeyViewModel by viewModels()

    private var _binding: FragmentApikeyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApikeyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        subscribeObservers()

        binding.setButton.setOnClickListener {
            cacheState()
            setApiKey()
            uiCommunicationListener.hideSoftKeyboard()
        }
    }

    override fun onResume() {
        viewModel.onTriggerEvent(SearchFragmentApiKeyEvents.FetchApiKey)
        super.onResume()
    }

    private fun subscribeObservers() {


        viewModel.state.observe(viewLifecycleOwner, {state->

            processQueue(
                context = context,
                queue = state.queue,
                stateMessageCallback = object: StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(SearchFragmentApiKeyEvents.OnRemoveHeadFromQueue)
                    }
                }
            )

            if(state.apikey == null) {
                setTextTitleNull()
            } else {
                state.apikey.let {
                    binding.currentApiKey.setText(R.string.current_api_key)
                    binding.appIdInfo.text = state.apikey?.app_id
                    binding.appKeyInfo.text = state.apikey?.app_key
                }
            }

        })

    }

    private fun setTextTitleNull() {
        binding.currentApiKey.setText(R.string.no_apikey_found)
        binding.appIdInfo.text = ""
        binding.appKeyInfo.text = ""
    }

    private fun setApiKey() {
        viewModel.onTriggerEvent(
            SearchFragmentApiKeyEvents.SetApiKey(
            app_id = binding.inputAppId.text.toString(),
            app_key = binding.inputAppKey.text.toString()
        ))
        viewModel.onTriggerEvent(SearchFragmentApiKeyEvents.AppendToMessageQueue(
            stateMessage = StateMessage(
                response = Response(
                    message = "ApiKey Updated",
                    uiComponentType = UIComponentType.Toast,
                    messageType = MessageType.None
                )
            )
        ))
    }

    private fun cacheState() {
        viewModel.onTriggerEvent(SearchFragmentApiKeyEvents.OnUpdateAppId(binding.inputAppId.toString()))
        viewModel.onTriggerEvent(SearchFragmentApiKeyEvents.OnUpdateAppKey(binding.inputAppKey.toString()))
    }



}
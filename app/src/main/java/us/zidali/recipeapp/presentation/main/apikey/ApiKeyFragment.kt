package us.zidali.recipeapp.presentation.main.apikey

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import us.zidali.recipeapp.R
import us.zidali.recipeapp.business.domain.util.*
import us.zidali.recipeapp.databinding.FragmentApikeyBinding
import us.zidali.recipeapp.presentation.util.processQueue

class ApiKeyFragment : BaseApiKeyFragment()
{

    private val viewModel: ApiKeyViewModel by viewModels()

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
        viewModel.onTriggerEvent(ApiKeyEvents.FetchApiKey)
        super.onResume()
    }

    private fun subscribeObservers() {


        viewModel.state.observe(viewLifecycleOwner, {state->

            processQueue(
                context = context,
                queue = state.queue,
                stateMessageCallback = object: StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(ApiKeyEvents.OnRemoveHeadFromQueue)
                    }
                }
            )

            if(state.apikey == null) {
                setTextTitleNull()
            } else {
                state.apikey.let {
                    binding.currentApiKey.setText(R.string.current_api_key)
                    binding.appIdInfo.text = getString(R.string.app_id) + state.apikey?.app_id
                    binding.appKeyInfo.text = getString(R.string.app_key) + state.apikey?.app_key
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
        viewModel.onTriggerEvent(ApiKeyEvents.SetApiKey(
            app_id = binding.inputAppId.text.toString(),
            app_key = binding.inputAppKey.text.toString()
        ))
        viewModel.onTriggerEvent(ApiKeyEvents.AppendToMessageQueue(
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
        viewModel.onTriggerEvent(ApiKeyEvents.OnUpdateAppId(binding.inputAppId.toString()))
        viewModel.onTriggerEvent(ApiKeyEvents.OnUpdateAppKey(binding.inputAppKey.toString()))
    }

}
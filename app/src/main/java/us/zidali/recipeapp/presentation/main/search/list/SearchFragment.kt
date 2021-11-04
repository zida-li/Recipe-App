package us.zidali.recipeapp.presentation.main.search.list

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import us.zidali.recipeapp.R
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.*
import us.zidali.recipeapp.databinding.FragmentSearchBinding
import us.zidali.recipeapp.presentation.main.search.BaseSearchFragment
import us.zidali.recipeapp.presentation.util.TopSpacingItemDecoration
import us.zidali.recipeapp.presentation.util.processQueue

class SearchFragment : BaseSearchFragment(),
    SearchListAdapter.Interaction
{

    private val viewModel: SearchViewModel by viewModels()
    private var recyclerAdapter: SearchListAdapter? = null
    private lateinit var menu: Menu
    private lateinit var searchView: SearchView

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()
    }

    override fun onResume() {
        if (viewModel.state.value?.recipeList?.size!! > 0) {
            try {
                viewModel.onTriggerEvent(SearchEvents.CompareSearchToFavorite)
            } catch (e: Exception) {

            }
        }
        super.onResume()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner, {state ->

            uiCommunicationListener.displayProgressBar(state.isLoading)

            processQueue(
                context = context,
                queue = state.queue,
                stateMessageCallback = object: StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(SearchEvents.OnRemoveHeadFromQueue)
                    }
                })

            recyclerAdapter?.apply {
                submitList(recipeList = state.recipeList)
                recyclerAdapter?.notifyDataSetChanged()
            }

        })
    }

    private fun initSearchView() {
        activity?.apply {
            val searchManager: SearchManager = getSystemService(SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.setIconifiedByDefault(true)
            searchView.isSubmitButtonEnabled = true
        }

        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText

        viewModel.state.value?.let { state->
            if(state.query.isNotBlank()){
                searchPlate.setText(state.query)
//                searchView.isIconified = false
//                binding.focusableView.requestFocus()
            }
        }

        searchPlate.setOnEditorActionListener{ v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                || actionId == EditorInfo.IME_ACTION_SEARCH) {
                  val searchQuery = v.text.toString()
                executeNewQuery(searchQuery)
             }
            true
        }

        val searchButton = searchView.findViewById(R.id.search_go_btn) as View
        searchButton.setOnClickListener{
            val searchQuery = searchPlate.text.toString()
            executeNewQuery(searchQuery)
        }

    }

    private fun initRecyclerView() {
        binding.fragmentSearchRecyclerview.apply {
            layoutManager = GridLayoutManager(this@SearchFragment.context, 2)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator)
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = SearchListAdapter(this@SearchFragment)
            addOnScrollListener(object: RecyclerView.OnScrollListener(){

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (
                        lastPosition == recyclerAdapter?.itemCount?.minus(1)
//                        && viewModel.state.value?.isLoading == false
//                        && viewModel.state.value?.moreResultAvailable == true
                    ) {
                        if((lastPosition + 1) >= (viewModel.state.value?.page!! * viewModel.state.value?.pageSize!!)) {
//                            Log.d(TAG, "SearchFragment: nextPage() Triggered")
                            viewModel.onTriggerEvent(SearchEvents.NextPage)
                        }
                    }
                }
            })
            adapter = recyclerAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
        inflater.inflate(R.menu.search_fragment_menu, this.menu)
        initSearchView()
    }

    private fun executeNewQuery(query: String) {
        viewModel.onTriggerEvent(SearchEvents.UpdateQuery(query))
        viewModel.onTriggerEvent(SearchEvents.NewSearch)
        resetUI()
    }

    private fun resetUI() {
        uiCommunicationListener.hideSoftKeyboard()
        binding.focusableView.requestFocus()
    }

    override fun onItemSelected(position: Int, item: Recipe) {
        try {
            viewModel.onTriggerEvent(SearchEvents.SaveToTemporaryRecipeDb(item))
            viewModel.state.value?.let {state->
                val bundle = bundleOf("recipeName" to item.recipeName)
                findNavController().navigate(R.id.action_searchFragment_to_viewRecipeFragment, bundle)
            }?: throw Exception("Null Recipe")
        } catch (e: Exception) {
            e.printStackTrace()
            viewModel.onTriggerEvent(
                SearchEvents.AppendToMessageQueue(
                    stateMessage = StateMessage(
                        response = Response(
                            message = e.message,
                            uiComponentType = UIComponentType.Dialog,
                            messageType = MessageType.Error
                        )
                    )
                )
            )
        }
    }

    override fun onFavoriteIconClicked(position: Int, item: Recipe) {
        viewModel.onTriggerEvent(SearchEvents.SaveOrDeleteRecipeFromDb(item))
        recyclerAdapter?.notifyItemChanged(position, item)

        if(item.isFavorite) {
            viewModel.onTriggerEvent(
                SearchEvents.AppendToMessageQueue(
                    stateMessage = StateMessage(
                        response = Response(
                            message = "${item.recipeName} Added To Favorites",
                            uiComponentType = UIComponentType.Toast,
                            messageType = MessageType.None
                        )
                    )
                )
            )
        } else {
            viewModel.onTriggerEvent(
                SearchEvents.AppendToMessageQueue(
                    stateMessage = StateMessage(
                        response = Response(
                            message = "${item.recipeName} Removed From Favorites",
                            uiComponentType = UIComponentType.Toast,
                            messageType = MessageType.None
                        )
                    )
                )
            )
        }

    }

}
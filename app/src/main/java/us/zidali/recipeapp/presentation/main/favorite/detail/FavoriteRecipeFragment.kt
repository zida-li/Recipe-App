package us.zidali.recipeapp.presentation.main.favorite.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import us.zidali.recipeapp.R
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.databinding.FragmentViewRecipeBinding
import us.zidali.recipeapp.presentation.main.favorite.BaseFavoriteFragment

class FavoriteRecipeFragment: BaseFavoriteFragment() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.empty_plate)
        .error(R.drawable.empty_plate)

    private val viewModel: FavoriteRecipeViewModel by viewModels()
    private var recyclerAdapter: FavoriteIngredientListAdapter? = null

    private var _binding: FragmentViewRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()

        binding.addToShoppingListButton.setOnClickListener{

            if(viewModel.state.value?.recipe!!.isInShoppingList) {
                viewModel.state.value?.recipe!!.isInShoppingList = false
                refreshButtonState()
                viewModel.onTriggerEvent(FavoriteRecipeEvents.DeleteRecipeFromShoppingList)
            } else {
                viewModel.state.value?.recipe!!.isInShoppingList = true
                refreshButtonState()
                viewModel.onTriggerEvent(FavoriteRecipeEvents.AddToShoppingList)
            }
        }
    }

    override fun onResume() {
        if(viewModel.state.value?.recipe != null) {
            viewModel.onTriggerEvent(FavoriteRecipeEvents.CompareFavoriteToShoppingList)
//            Log.d(TAG, "onResume: ${viewModel.state.value?.recipe!!.isInShoppingList}")
        }
        super.onResume()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner, {state->

            state.recipe?.let {
                setRecipeProperties(it)
                refreshButtonState()
            }

            state.recipe?.recipeIngredientCheck.let {

                if(it != null) {
                    recyclerAdapter?.apply {
                        submitList(it)
                    }
                }

            }

        })

    }

    private fun initRecyclerView() {
        binding.detailRecipeIngredients.apply {
            layoutManager = LinearLayoutManager(this@FavoriteRecipeFragment.context)
            recyclerAdapter = FavoriteIngredientListAdapter()
            adapter = recyclerAdapter
        }
    }

    private fun refreshButtonState() {
//        Log.d(TAG, "refreshButtonState: ${viewModel.state.value?.recipe!!.isInShoppingList}")
        if(viewModel.state.value?.recipe!!.isInShoppingList) {
            showDeleteToShoppingListButton()
        } else {
            showAddToShoppingListButton()
        }
    }

    private fun showAddToShoppingListButton() {
        binding.addToShoppingListButton.setText(R.string.add_to_shoppingList)
    }

    private fun showDeleteToShoppingListButton() {
        binding.addToShoppingListButton.setText(R.string.delete_from_shoppingList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipe_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_delete_favorite_detail -> {
                viewModel.onTriggerEvent(FavoriteRecipeEvents.DeleteRecipe)
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecipeProperties(recipe: Recipe) {
        Glide.with(this)
            .setDefaultRequestOptions(requestOptions)
            .load(recipe.recipeImageUrl)
            .into(binding.detailRecipeImage)
        binding.detailRecipeTitle.text = recipe.recipeName
    }
}
package us.zidali.recipeapp.presentation.main.shoppinglist.expandable

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import us.zidali.recipeapp.R
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.databinding.ShoppingListParentBinding

class ShoppingListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedRecipe: LiveData<ArrayList<Recipe>>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.recipeId == newItem.recipeId
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ShoppingListViewHolder(
            ShoppingListParentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction,
            lifecycleOwner,
            selectedRecipe,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ShoppingListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Recipe>) {
        differ.submitList(list)
    }

    class ShoppingListViewHolder
    constructor(
        private val binding: ShoppingListParentBinding,
        private val interaction: Interaction?,
        private val lifecycleOwner: LifecycleOwner,
        private val selectedRecipe: LiveData<ArrayList<Recipe>>,
    ) : RecyclerView.ViewHolder(binding.root), IngredientListAdapter.Interaction {

        private var ingredientListAdapter = IngredientListAdapter()

        private lateinit var mRecipe: Recipe

        fun bind(item: Recipe) = with(itemView) {

            binding.icon.setImageResource(if(item.isExpanded) R.drawable.collapse else R.drawable.expand)

            initIngredientListAdapter(binding, context)

            expandOnInit(item, item.isExpanded, context)

            itemView.setOnClickListener {

                interaction?.onItemSelected(adapterPosition, item)
                expandOnClicked(item, item.isExpanded, context)
                interaction?.expand(!item.isExpanded, item)
                animateIsExpand(binding, item)
            }

            itemView.setOnLongClickListener {
                interaction?.activateMultiSelectionMode()
                interaction?.onItemSelected(adapterPosition, mRecipe)
                true
            }

            mRecipe = item

            binding.parentTextTitle.text = item.recipeName

            selectedRecipe.observe(lifecycleOwner, {recipe->
                if (recipe != null) {
                    if (recipe.contains(mRecipe)) {
                        binding.shoppingListCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.multiSelectionModeColor))
                    }
                    else {
                        binding.shoppingListCardView.setBackgroundColor(Color.WHITE)
                    }
                } else {
                    binding.shoppingListCardView.setBackgroundColor(Color.WHITE)
                }
            })
        }

        private fun initIngredientListAdapter(binding: ShoppingListParentBinding, context: Context) {
            binding.shoppingListParentRecyclerview.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false)

            ingredientListAdapter = IngredientListAdapter(
                this@ShoppingListViewHolder
            )

            binding.shoppingListParentRecyclerview.adapter = ingredientListAdapter
        }

        private fun expandOnInit(item: Recipe, isExpanded: Boolean, context: Context) {
            if(isExpanded) {
//                Log.d("AppDebug", "ShoppingListAdapter: expandOnInit() expand ${item.recipeName}")
                ingredientListAdapter.submitList(
                    item.recipeIngredientCheck!!
                )
            } else if (!isExpanded){
//                Log.d("AppDebug", "ShoppingListAdapter: expandOnInit() else ${item.recipeName}")
                ingredientListAdapter.submitList(
                    item.recipeIngredientCheckEmpty
                )
            }
        }

        private fun expandOnClicked(item: Recipe, isExpanded: Boolean, context: Context) {
            if (!item.isMultiSelectionModeEnabled){
                if (!isExpanded) {
//                Log.d("AppDebug", "ShoppingListAdapter: expandOnClicked() expand ${item.recipeName}")
                    ingredientListAdapter.submitList(
                        item.recipeIngredientCheck!!
                    )
                } else if (isExpanded) {
                    ingredientListAdapter.submitList(
                        item.recipeIngredientCheckEmpty
                    )
//                Log.d("AppDebug", "ShoppingListAdapter: expandOnClicked() else ${item.recipeName}")
                }
            }
        }

        private fun animateIsExpand(binding: ShoppingListParentBinding, item: Recipe) {
            binding.icon.visibility = View.VISIBLE
            binding.icon.setImageResource(if(item.isExpanded) R.drawable.collapse_animated else R.drawable.expand_animated)
            val drawable = binding.icon.drawable as Animatable
            drawable.start()
        }

        override fun onIsCheckedClickedIngredientListAdapter(item: Recipe.Ingredient) {
            interaction?.onIsCheckedClicked(item)
        }
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Recipe)

        fun activateMultiSelectionMode()

        fun isMultiSelectionModeEnabled(): Boolean

        fun expand(isExpanded: Boolean, recipe: Recipe)

        fun onIsCheckedClicked(item: Recipe.Ingredient)

    }
}
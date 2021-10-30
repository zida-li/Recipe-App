package com.example.recipeappfivelearning.presentation.main.shoppinglist.expandable

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.ShoppingListParentBinding

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

    return when(viewType) {

            else -> {ShoppingListViewHolder(
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
    }
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
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var mRecipe: Recipe

        fun bind(item: Recipe) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.setOnLongClickListener {
                interaction?.activateMultiSelectionMode()
                interaction?.onItemSelected(adapterPosition, mRecipe)
                true
            }

            val ingredientListAdapter = IngredientListAdapter()
            binding.shoppingListParentRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            ingredientListAdapter.submitList(
                item.recipeIngredientCheck!!
            )
            binding.shoppingListParentRecyclerview.adapter = ingredientListAdapter

            mRecipe = item

            binding.parentTextTitle.text = item.recipeName

            selectedRecipe.observe(lifecycleOwner, {recipe->
                if (recipe != null) {
                    if (recipe.contains(mRecipe)) {
                        binding.shoppingListCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor))
                    }
                    else {
                        binding.shoppingListCardView.setBackgroundColor(Color.WHITE)
                    }
                } else {
                    binding.shoppingListCardView.setBackgroundColor(Color.WHITE)
                }
            })
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
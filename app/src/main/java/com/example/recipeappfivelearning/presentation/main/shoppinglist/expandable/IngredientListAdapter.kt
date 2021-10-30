package com.example.recipeappfivelearning.presentation.main.shoppinglist.expandable

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.ShoppingListChildBinding

class IngredientListAdapter(private val interaction: Interaction? = null) :
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

        return ShoppingListChildViewHolder(
            ShoppingListChildBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ShoppingListChildViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Recipe>) {
        differ.submitList(list)
    }

    class ShoppingListChildViewHolder
    constructor(
        private val binding: ShoppingListChildBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recipe) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }



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
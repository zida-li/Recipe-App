package com.example.recipeappfivelearning.presentation.main.shoppinglist.expandable

import android.graphics.Paint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.ShoppingListChildBinding

class IngredientListAdapter(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe.Ingredient>() {

        override fun areItemsTheSame(
            oldItem: Recipe.Ingredient,
            newItem: Recipe.Ingredient
        ): Boolean {
            return oldItem.recipeName == newItem.recipeName
        }

        override fun areContentsTheSame(
            oldItem: Recipe.Ingredient,
            newItem: Recipe.Ingredient
        ): Boolean {
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
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Recipe.Ingredient>) {
        differ.submitList(list)
    }

    class ShoppingListChildViewHolder
    constructor(
        private val binding: ShoppingListChildBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recipe.Ingredient) = with(itemView) {

            checkCheckedOnInit(item, item.isChecked)

            itemView.setOnClickListener {
                checkItemClicked(item)
                interaction?.onIsCheckedClickedIngredientListAdapter(item)
            }

            binding.childTextTitle.text = item.recipeIngredient

        }

        private fun checkCheckedOnInit(
            item: Recipe.Ingredient,
            isChecked: Boolean,
        ) {
            if (isChecked) {
//                Log.d("AppDebug", "ShoppingListAdapter: checked")
                binding.childTextTitle.apply {
                    text = item.recipeIngredient
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            } else if (!isChecked) {
//                Log.d("AppDebug", "ShoppingListAdapter: else")
                binding.childTextTitle.apply {
                    text = item.recipeIngredient
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }

        private fun checkItemClicked(item: Recipe.Ingredient) {
            binding.childTextTitle.apply {
                if (!paint.isStrikeThruText) {
//                    Log.d("AppDebug", "IngredientListAdapter: checked")
                    text = item.recipeIngredient
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
//                    Log.d("AppDebug", "IngredientListAdapter: else")
                    binding.childTextTitle.apply {
                        text = item.recipeIngredient
                        paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
                }
            }
        }
    }

    interface Interaction {
        fun onIsCheckedClickedIngredientListAdapter(item: Recipe.Ingredient)
    }
}
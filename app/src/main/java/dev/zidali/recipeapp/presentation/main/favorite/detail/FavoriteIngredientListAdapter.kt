package dev.zidali.recipeapp.presentation.main.favorite.detail

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.databinding.FragmentViewRecipeIngredientcardBinding

class FavoriteIngredientListAdapter(private val interaction: Interaction? = null) :
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

        return FavoriteIngredientViewHolder(
            FragmentViewRecipeIngredientcardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteIngredientViewHolder -> {
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

    class FavoriteIngredientViewHolder
    constructor(
        private val binding: FragmentViewRecipeIngredientcardBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recipe.Ingredient) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.fragmentFavoriteRecipeIngredient.text = item.recipeIngredient

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Recipe.Ingredient)
    }
}
package com.example.recipeappfivelearning.presentation.main.favorite.list

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.LayoutFavoriteListItemBinding

class FavoriteListAdapter(
    private val interaction: Interaction? = null,
    private val lifecycleOwner: LifecycleOwner,
    private val selectedRecipe: LiveData<ArrayList<Recipe>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.empty_plate)
        .error(R.drawable.empty_plate)

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

        return FavoriteViewHolder(
            LayoutFavoriteListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction,
            requestOptions,
            lifecycleOwner,
            selectedRecipe,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(recipeList: List<Recipe>) {
        differ.submitList(recipeList)
    }

    class FavoriteViewHolder
    constructor(
        private val binding: LayoutFavoriteListItemBinding,
        private val interaction: Interaction?,
        private val requestOptions: RequestOptions,
        private val lifecycleOwner: LifecycleOwner,
        private val selectedRecipe: LiveData<ArrayList<Recipe>>
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var mRecipe: Recipe

        fun bind(item: Recipe) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            setOnLongClickListener {
                interaction?.activateMultiSelectionMode()
                interaction?.onItemSelected(adapterPosition, mRecipe)
                true
            }
            mRecipe = item

            Glide.with(binding.root)
                .setDefaultRequestOptions(requestOptions)
                .load(item.recipeImageUrl)
                .transition(withCrossFade())
                .into(binding.favoriteListImage)
            binding.favoriteListName.text = item.recipeName

            selectedRecipe.observe(lifecycleOwner, {recipe->
                if (recipe != null) {
                    if (recipe.contains(mRecipe)) {
                        binding.cardViewFavoriteFragment.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor))
                    }
                    else {
                        binding.cardViewFavoriteFragment.setBackgroundColor(Color.WHITE)
                    }
                } else {
                    binding.cardViewFavoriteFragment.setBackgroundColor(Color.WHITE)
                }
            })

        }
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Recipe)

        fun activateMultiSelectionMode()

        fun isMultiSelectionModeEnabled(): Boolean

        fun isRecipeSelected(recipe: Recipe): Boolean

    }
}
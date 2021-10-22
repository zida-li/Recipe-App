package com.example.recipeappfivelearning.presentation.main.search.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.LayoutSearchListItemBinding

class SearchListAdapter(
    private val interaction: Interaction? = null
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

        return SearchViewHolder(
            LayoutSearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction,
            requestOptions
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(recipeList: List<Recipe>) {
        differ.submitList(recipeList)
    }

    class SearchViewHolder
    constructor(
        private val binding: LayoutSearchListItemBinding,
        private val interaction: Interaction?,
        private val requestOptions: RequestOptions
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recipe) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.favoriteIconSearch.setOnClickListener {
                interaction?.onFavoriteIconClicked(adapterPosition, item)
            }

            if(item.isFavorite) {
                binding.favoriteIconSearch.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.favoriteIconSearch.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            Glide.with(binding.root)
                .setDefaultRequestOptions(requestOptions)
                .load(item.recipeImageUrl)
                .transition(withCrossFade())
                .into(binding.searchListImage)
            binding.searchListName.text = item.recipeName
        }
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Recipe)

        fun onFavoriteIconClicked(position: Int, item: Recipe)
    }
}
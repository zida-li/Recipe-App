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
import com.example.recipeappfivelearning.databinding.LayoutSearchListItemBinding

class SearchListAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.empty_plate)
        .error(R.drawable.empty_plate)

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchState.SearchStateRecipeModel>() {

        override fun areItemsTheSame(oldItem: SearchState.SearchStateRecipeModel, newItem: SearchState.SearchStateRecipeModel): Boolean {
            return oldItem.recipeId == newItem.recipeId
        }

        override fun areContentsTheSame(oldItem: SearchState.SearchStateRecipeModel, newItem: SearchState.SearchStateRecipeModel): Boolean {
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
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(recipeList: List<SearchState.SearchStateRecipeModel>) {
        differ.submitList(recipeList)
    }

    class SearchViewHolder
    constructor(
        private val binding: LayoutSearchListItemBinding,
        private val interaction: Interaction?,
        private val requestOptions: RequestOptions
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchState.SearchStateRecipeModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
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
        fun onItemSelected(position: Int, item: SearchState.SearchStateRecipeModel)
    }
}
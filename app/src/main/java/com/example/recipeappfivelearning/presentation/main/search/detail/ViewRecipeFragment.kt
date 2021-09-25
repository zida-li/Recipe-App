package com.example.recipeappfivelearning.presentation.main.search.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.request.RequestOptions
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.databinding.FragmentViewRecipeBinding
import com.example.recipeappfivelearning.presentation.main.search.detail.viewmodel.ViewRecipeViewModel
import com.example.recipeappfivelearning.presentation.main.search.list.BaseSearchFragment

class ViewRecipeFragment: BaseSearchFragment() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.empty_plate)
        .error(R.drawable.empty_plate)

    private val viewModel: ViewRecipeViewModel by viewModels()

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
        subscribeObservers()
    }

    private fun subscribeObservers() {

    }
}
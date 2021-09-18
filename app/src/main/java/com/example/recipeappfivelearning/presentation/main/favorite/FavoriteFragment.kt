package com.example.recipeappfivelearning.presentation.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.recipeappfivelearning.databinding.FragmentFavoriteBinding
import com.example.recipeappfivelearning.presentation.main.favorite.viewmodel.FavoriteViewModel

class FavoriteFragment : BaseFavoriteFragment() {

    private val viewModel: FavoriteViewModel by viewModels()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }
}
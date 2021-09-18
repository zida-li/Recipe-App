package com.example.recipeappfivelearning.presentation.main.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.recipeappfivelearning.databinding.FragmentShoppingListBinding
import com.example.recipeappfivelearning.presentation.main.shoppinglist.viewmodel.ShoppingListViewModel

class ShoppingListFragment : BaseShoppingListFragment (){

    private val viewModel: ShoppingListViewModel by viewModels()

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }
}
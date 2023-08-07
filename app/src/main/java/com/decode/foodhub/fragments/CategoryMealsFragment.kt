package com.decode.foodhub.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.decode.foodhub.adapter.CategoryMealsAdapter
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentCategoryMealsBinding
import com.decode.foodhub.utils.NetworkResult
import com.decode.foodhub.utils.initRecycler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryMealsFragment :
    BaseFragment<MainViewModel, FragmentCategoryMealsBinding>(FragmentCategoryMealsBinding::inflate) {
    override val viewModel: MainViewModel by viewModels()
    private val args: CategoryMealsFragmentArgs by navArgs()
    private val adapter: CategoryMealsAdapter by lazy {
        CategoryMealsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMeals(args.categoryName)
    }

    override fun observeEvents() {
        randomMeals()
    }

    override fun onCreateFinished() {
        rcvInit()
    }

    private fun randomMeals() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.meal.collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            adapter.differ.submitList(it.data.meals)
                            binding.textView.text = "${args.categoryName}: ${adapter.differ.currentList.size}"
                        }

                        is NetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "There is something wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        NetworkResult.Loading -> {

                        }

                        NetworkResult.Empty -> {}
                    }
                }
            }
        }
    }

    private fun rcvInit() {
        binding.rv.initRecycler(
            GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            ), adapter
        )
    }
}
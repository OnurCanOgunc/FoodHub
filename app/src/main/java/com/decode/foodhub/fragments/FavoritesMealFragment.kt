package com.decode.foodhub.fragments

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.decode.foodhub.adapter.FavoritesAdapter
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentFavoritesMealBinding
import com.decode.foodhub.utils.initRecycler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesMealFragment :
    BaseFragment<MainViewModel, FragmentFavoritesMealBinding>(FragmentFavoritesMealBinding::inflate) {
    override val viewModel: MainViewModel by viewModels()

    private val adapter: FavoritesAdapter by lazy {
        FavoritesAdapter()
    }


    override fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favMeals.collect{
                    adapter.deffer.submitList(it)
                }
            }
        }
    }

    override fun onCreateFinished() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvFav.initRecycler(
            GridLayoutManager(
                requireContext(),
                3,
                GridLayoutManager.VERTICAL,
                false
            ), adapter
        )
    }

}
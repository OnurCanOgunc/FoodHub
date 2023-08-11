package com.decode.foodhub.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.decode.foodhub.adapter.FavoritesAdapter
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentFavoritesMealBinding
import com.decode.foodhub.models.MealX
import com.decode.foodhub.utils.initRecycler
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesMealFragment :
    BaseFragment<MainViewModel, FragmentFavoritesMealBinding>(FragmentFavoritesMealBinding::inflate) {
    override val viewModel: MainViewModel by viewModels()

    private val adapter: FavoritesAdapter by lazy {
        FavoritesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllMeals()
    }

    override fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favMeals.collect {
                    adapter.differ.submitList(it)
                    binding.favSize = it.size
                }
            }
        }
    }

    override fun onCreateFinished() {
        initRecyclerView()
        mealDelete()
        navigate()
    }
    private fun mealDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = adapter.differ.currentList[position]
                viewModel.deleteMeal(meal)
                Snackbar.make(requireView(), "${meal.strMeal} Silindi", Snackbar.LENGTH_SHORT)
                    .setAction("Geri Al") {
                        viewModel.insertMeal(meal)
                    }.show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFav)
    }

    private fun navigate() {

        adapter.onItemClick = {
            val nav = FavoritesMealFragmentDirections.actionFavoritesMealFragmentToDetailMealFragment(it.idMeal)
            findNavController().navigate(nav)
        }
    }

    private fun initRecyclerView() {
        binding.rvFav.initRecycler(
            GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            ), adapter
        )
    }

}
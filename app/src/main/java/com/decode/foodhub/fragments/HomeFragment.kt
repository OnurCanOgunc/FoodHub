package com.decode.foodhub.fragments

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.decode.foodhub.adapter.CategoryAdapter
import com.decode.foodhub.adapter.ViewPagerAdapter
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentHomeBinding
import com.decode.foodhub.models.Meal
import com.decode.foodhub.models.MealX
import com.decode.foodhub.models.RandomMeals
import com.decode.foodhub.utils.NetworkResult
import com.decode.foodhub.utils.initRecycler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<MainViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    private var mealList: ArrayList<Meal> = ArrayList()
    private lateinit var adapter: ViewPagerAdapter

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getrandomMeals()
        viewModel.getCategory()

    }

    override fun observeEvents() {
        randomMeals()
        category()
    }

    override fun onCreateFinished() {
        initRecyclerView()
        categoryOnItemClick()
        searchMeal()
    }

    private fun searchMeal() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                //filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //filterList(newText)
                return true
            }

        })
    }

    private fun filterList(newText: String?) {
        if (newText != null) {
            viewModel.searchMeal(newText)
            val filteredList = ArrayList<MealX>()

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.mealSearch.collect {
                        when(it) {
                            is NetworkResult.Success -> {
                                Log.e("dever", it.data.meals!![0].strMeal!!)
                            }
                            is NetworkResult.Error -> {}
                            else -> {}
                        }
                    }
                }
            }

        }
    }

    private fun setAdapter() {
        adapter = ViewPagerAdapter(mealList, requireContext())
        binding.apply {
            viewPager.adapter = adapter
            tabs.setupWithViewPager(viewPager)
        }
    }


    private fun imgUrlList(meal: RandomMeals) {
        mealList.clear()
        meal.meals?.forEach {
            it?.let { it1 -> mealList.add(it1) }
        }
    }

    private fun initRecyclerView() {
        binding.rcvCategories.initRecycler(
            GridLayoutManager(
                requireContext(),
                3,
                GridLayoutManager.VERTICAL,
                false
            ), categoryAdapter
        )
    }

    private fun categoryOnItemClick() {
        categoryAdapter.onItemClick = {
            val nav =
                HomeFragmentDirections.actionHomeFragmentToCategoryMealsFragment(it.strCategory!!)
            findNavController().navigate(nav)
        }
    }

    private fun viewPagerOnItemClick() {
        adapter.onItemClick = {
            val nav = HomeFragmentDirections.actionHomeFragmentToDetailMealFragment(it.idMeal!!)
            findNavController().navigate(nav)
        }
    }

    private fun randomMeals() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomMeal.collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            imgUrlList(it.data)
                            setAdapter()
                            viewPagerOnItemClick()
                        }

                        is NetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "There is something wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun category() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            categoryAdapter.differ.submitList(it.data.categories)

                        }

                        is NetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "There is something wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}
package com.decode.foodhub.fragments

import android.os.Bundle
import android.widget.Toast
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
import com.decode.foodhub.models.RandomMeals
import com.decode.foodhub.utils.Constants.CATEGORY_NAME
import com.decode.foodhub.utils.NetworkResult
import com.decode.foodhub.utils.initRecycler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<MainViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    private var urlList: ArrayList<String> = ArrayList()
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
        onItemClick()
    }

    private fun setAdapter() {
        adapter = ViewPagerAdapter(urlList, requireContext())
        binding.apply {
            viewPager.adapter = adapter
            tabs.setupWithViewPager(viewPager)

        }
    }

    private fun imgUrlList(meal: RandomMeals) {
        urlList.clear()
        meal.meals?.forEach {
            it?.strMealThumb?.let { it1 -> urlList.add(it1) }
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

    private fun onItemClick() {
        categoryAdapter.onItemClick = {
            val nav = HomeFragmentDirections.actionHomeFragmentToCategoryMealsFragment(it.strCategory!!)
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

                        NetworkResult.Loading -> {

                        }

                        NetworkResult.Empty -> {}
                    }
                }
            }
        }
    }

}
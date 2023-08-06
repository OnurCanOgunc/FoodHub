package com.decode.foodhub.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.decode.foodhub.adapter.ViewPagerAdapter
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentHomeBinding
import com.decode.foodhub.models.PopularMeals
import com.decode.foodhub.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<MainViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var urlList:ArrayList<String> = ArrayList()
    private lateinit var adapter:ViewPagerAdapter

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRandomMeals()

    }

    override fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomMeal.collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            imgUrlList(it.data)
                            setAdapter()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = ViewPagerAdapter(urlList,requireContext())
        binding.apply {
            viewPager.adapter = adapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun imgUrlList(popularMeals: PopularMeals) {
        popularMeals.recipes?.forEach {
            urlList.add(it?.image!!)
        }
    }

}
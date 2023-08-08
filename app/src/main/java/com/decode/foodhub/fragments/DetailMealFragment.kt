package com.decode.foodhub.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentDetailMealBinding
import com.decode.foodhub.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMealFragment :
    BaseFragment<MainViewModel, FragmentDetailMealBinding>(FragmentDetailMealBinding::inflate) {

    private val args: DetailMealFragmentArgs by navArgs()

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMealDetail(args.id)
    }

    override fun observeEvents() {
        detailMeal()
    }

    override fun onCreateFinished() {

    }

    private fun detailMeal() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mealDetail.collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            binding.detailMeal = it.data.meals?.get(0)

                        }

                        is NetworkResult.Error -> {
                            Log.e("HATAA", it.message.toString())
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}
package com.decode.foodhub.fragments

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentDetailMealBinding
import com.decode.foodhub.models.MealX
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
                            val meal = it.data.meals?.get(0)
                            binding.detailMeal = meal
                            insertMeal(meal)
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

    private fun insertMeal(mealX: MealX?) {
        binding.textSave.setOnClickListener {
            viewModel.insertMeal(mealX!!)
            Toast.makeText(requireContext(),"Tarif Kaydedildi",Toast.LENGTH_SHORT).show()
        }
    }
}
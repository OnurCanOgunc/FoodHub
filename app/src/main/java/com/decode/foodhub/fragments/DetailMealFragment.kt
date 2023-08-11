package com.decode.foodhub.fragments

import android.content.Intent
import android.net.Uri
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
        likedMeal()
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
                            navYoutube(meal)
                            shareMeal(meal)
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

    private fun navYoutube(meal: MealX?) {
        binding.fab.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meal?.strYoutube)))
        }
    }

    private fun likedMeal() {
        binding.textLike.setOnClickListener {
            Toast.makeText(requireContext(),"\uD83D\uDC4C",Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareMeal(meal: MealX?) {
        binding.textShare.setOnClickListener {
            val  intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE,meal?.strMeal)
                putExtra(Intent.EXTRA_TEXT,meal?.strYoutube)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }

    private fun insertMeal(mealX: MealX?) {
        binding.textSave.setOnClickListener {
            viewModel.insertMeal(mealX!!)
            Toast.makeText(requireContext(),"Tarif Kaydedildi",Toast.LENGTH_SHORT).show()
        }
    }
}
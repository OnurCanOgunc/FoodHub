package com.decode.foodhub.fragments

import androidx.fragment.app.viewModels
import com.decode.foodhub.base.BaseFragment
import com.decode.foodhub.databinding.FragmentFavoritesMealBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesMealFragment :
    BaseFragment<MainViewModel, FragmentFavoritesMealBinding>(FragmentFavoritesMealBinding::inflate) {
    override val viewModel: MainViewModel by viewModels()

    override fun observeEvents() {
        TODO("Not yet implemented")
    }

    override fun onCreateFinished() {
        TODO("Not yet implemented")
    }

}
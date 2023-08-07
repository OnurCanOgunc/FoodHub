package com.decode.foodhub.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.IllegalArgumentException

abstract class BaseFragment<VM: ViewModel, VB:ViewBinding>(
    private val bindingInflater:(inflater:LayoutInflater) -> VB
) : Fragment() {

    protected abstract val viewModel: VM

    private var _binding:VB? = null
    protected val binding: VB get() = _binding as VB

    protected abstract fun observeEvents()
    protected abstract fun onCreateFinished()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null) {
            throw IllegalArgumentException("Binding cannot be null")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateFinished()
        observeEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
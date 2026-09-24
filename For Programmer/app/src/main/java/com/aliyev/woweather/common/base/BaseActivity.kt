package com.aliyev.woweather.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
) : AppCompatActivity() {

    protected abstract val viewModel: VM

    protected abstract fun observeEvents()

    protected abstract fun onCreateFinished()


    private val _binding by lazy {
        bindingInflater.invoke(layoutInflater)
    }

    val binding: VB get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        enableEdgeToEdge()
        onCreateFinished()
        observeEvents()
    }


}
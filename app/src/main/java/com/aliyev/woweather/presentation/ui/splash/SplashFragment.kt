package com.aliyev.woweather.presentation.ui.splash

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aliyev.woweather.R
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.common.utils.reset
import com.aliyev.woweather.databinding.FragmentSplashBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()


    override fun observeEvents() {
        with(viewModel) {
            liveData.observe(viewLifecycleOwner) {
                when (it) {
                    is SplashUiState.IsCitySelected -> {
                        checkState(it.isSelected)
                    }

                    is SplashUiState.IsNetworkAvailable -> {
                        if (it.data) {
                            getIsCitySelected()
                        } else {
                            setupConnectivityAlertDialog().show()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreateFinish() {
        setup()
    }

    private fun setup() {

    }

    private fun checkState(_state: Boolean) {
        lifecycleScope.launch {
            delay(2500)
            if (_state) {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                )
            } else {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToSearchFragment()
                )
            }
        }
    }

    private fun setupConnectivityAlertDialog(): AlertDialog {
        val ad = MaterialAlertDialogBuilder(requireContext())
        ad.setCancelable(false)
            .setTitle(R.string.error)
            .setMessage(R.string.error_connection)
            .setPositiveButton(R.string.ok) { dialog, which ->
                requireActivity().reset()
            }
        return ad.create()
    }


}
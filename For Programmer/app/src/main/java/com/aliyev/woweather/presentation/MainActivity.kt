package com.aliyev.woweather.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.aliyev.woweather.R
import com.aliyev.woweather.common.base.BaseActivity
import com.aliyev.woweather.common.utils.gone
import com.aliyev.woweather.common.utils.navigationTextFont
import com.aliyev.woweather.common.utils.reset
import com.aliyev.woweather.common.utils.visible
import com.aliyev.woweather.databinding.ActivityMainBinding
import com.aliyev.woweather.domain.repository.networkObserve.ConnectivityObserver
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.isGone

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate) {
    override val viewModel: MainViewModel by viewModels()

    override fun observeEvents() {
        val ad = setupConnectivityAlertDialog()
        with(viewModel) {
            liveData.observe(this@MainActivity) {
                when (it) {
                    is MainUiState.Result -> {
                        if (it.data.name != ConnectivityObserver.Status.Available.name) {
                            ad.show()
                        } else {
                            ad.cancel()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateFinished() {
        setup()
    }

    private fun setup() {
        with(binding) {
            val navFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navFragment.navController
        }
    }

    private fun setupConnectivityAlertDialog(): AlertDialog {
        val ad = MaterialAlertDialogBuilder(this)
        ad.setCancelable(false)
            .setTitle(R.string.error)
            .setMessage(R.string.error_connection)
            .setPositiveButton(R.string.ok) { dialog, which ->
                reset()
            }
        return ad.create()
    }

}


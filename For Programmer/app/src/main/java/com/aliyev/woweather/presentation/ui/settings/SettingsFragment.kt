package com.aliyev.woweather.presentation.ui.settings

import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>(
    FragmentSettingsBinding::inflate
) {
    override val viewModel: SettingsViewModel by viewModels()

    override fun observeEvents() {
        with(viewModel) {
            liveData.observe(viewLifecycleOwner) {
                when (it) {
                    is SettingsUiState.IsFahrenheitSelected -> binding.switchButton.isChecked =
                        it.data
                }
            }

            effect.observe(viewLifecycleOwner) {
                when (it) {
                    is SettingsUiEffect.NavigateBack -> findNavController().popBackStack()
                }
            }
        }
    }

    override fun onViewCreateFinish() {
        binding.switchButton.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton?,
                isChecked: Boolean
            ) {
                viewModel.onClickSwithTemperature(isChecked)
            }
        })


        binding.buttonBack.setOnClickListener {
            viewModel.navigateBack()
        }
    }
}
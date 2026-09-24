package com.aliyev.woweather.presentation.ui.location

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.common.utils.gone
import com.aliyev.woweather.common.utils.progressDialog
import com.aliyev.woweather.common.utils.visible
import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.databinding.FragmentLocationManagerBinding
import com.aliyev.woweather.presentation.ui.location.adapters.SavedLocationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationManagerFragment :
    BaseFragment<FragmentLocationManagerBinding, LocationViewModel>(FragmentLocationManagerBinding::inflate) {

    override val viewModel: LocationViewModel by viewModels()
    private val locationAdapter = SavedLocationAdapter()

    override fun observeEvents() {
        with(binding) {
            with(viewModel) {
                val pd = progressDialog(requireContext())
                liveData.observe(viewLifecycleOwner) {
                    when (it) {
                        is LocationUiState.SavedCities -> {
                            pd.cancel()
                            if (it.data.isEmpty()) {
                                rvLocation.gone()
                                layoutEmpty.visible()
                            } else {
                                rvLocation.visible()
                                layoutEmpty.gone()
                                locationAdapter.submitData(it.data)
                            }
                        }

                        is LocationUiState.DeleteComplete -> viewModel.getSavedCities()
                        is LocationUiState.Error -> pd.cancel()
                        is LocationUiState.Loading -> pd.show()
                        else -> Unit
                    }
                }
                effect.observe(viewLifecycleOwner) {
                    when (it) {
                        is LocationUiEffect.ShowMessage -> Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


            }
        }
    }

    override fun onViewCreateFinish() {
        setup()
    }

    private fun setup() {
        setRV()
        viewModel.getSavedCities()
        with(binding) {
            buttonBack.setOnClickListener { findNavController().popBackStack() }
            buttonAddLocation.setOnClickListener {
                findNavController().navigate(
                    LocationManagerFragmentDirections.actionLocationManagerFragmentToSearchFragment()
                )
            }
        }
    }

    private fun setRV() {
        with(binding) {
            rvLocation.adapter = locationAdapter

            locationAdapter.onClickLocationItem = {
                it.token?.let { id ->
                    viewModel.setCityToken(id)
                    findNavController().popBackStack()
                }
            }

            locationAdapter.onClickDeleteLocation = {
                val savedCityDTO = SavedCityDTO(
                    it.id, it.name, it.region, it.country, it.token
                )
                viewModel.deleteSavedCity(savedCityDTO)
            }
        }
    }

}
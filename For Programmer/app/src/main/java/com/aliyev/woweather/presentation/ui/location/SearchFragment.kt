package com.aliyev.woweather.presentation.ui.location

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.common.utils.gone
import com.aliyev.woweather.common.utils.makeCityToken
import com.aliyev.woweather.common.utils.progressDialog
import com.aliyev.woweather.common.utils.visible
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO
import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.databinding.FragmentSearchBinding
import com.aliyev.woweather.presentation.ui.location.adapters.RecentSearchAdapter
import com.aliyev.woweather.presentation.ui.location.adapters.SearchLocationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding, LocationViewModel>(FragmentSearchBinding::inflate) {

    override val viewModel: LocationViewModel by viewModels()
    private val searchAdapter = SearchLocationAdapter()
    private val recentSearchAdapter = RecentSearchAdapter()
    private var _isSelected: Boolean = false
    private var job: Job? = null

    override fun observeEvents() {
        with(binding) {
            with(viewModel) {
                val pb = progressDialog(requireContext())
                liveData.observe(viewLifecycleOwner) {
                    when (it) {
                        LocationUiState.Error -> pb.cancel()
                        LocationUiState.Loading -> pb.show()
                        is LocationUiState.RecentSearches -> {
                            pb.cancel()
                            if (it.data.isEmpty()) {
                                layoutEmpty.visible()
                                rvSearch.gone()
                                layoutRecentSearch.gone()
                            } else {
                                layoutEmpty.gone()
                                rvSearch.gone()
                                layoutRecentSearch.visible()
                                recentSearchAdapter.submitData(it.data)
                            }
                        }

                        is LocationUiState.SearchCity -> {
                            pb.cancel()
                            searchAdapter.submitData(it.result)
                            if (it.result.isEmpty()) {
                                layoutEmpty.visible()
                                layoutRecentSearch.gone()
                                rvSearch.gone()
                            } else {
                                if (!rvSearch.isVisible) {
                                    layoutEmpty.gone()
                                    layoutRecentSearch.gone()
                                    rvSearch.visible()
                                }
                            }
                        }
                        is LocationUiState.IsCitySelected -> {
                            if (it.isSelected) buttonBack.visible() else buttonBack.gone()
                            _isSelected = it.isSelected
                        }
                        else -> Unit
                    }
                }

                effect.observe(viewLifecycleOwner) {
                    when (it) {
                        is LocationUiEffect.ShowMessage -> {
                            it.message?.let { message ->
                                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                            }
                        }

                        else -> Unit
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
        setSearch()

        viewModel.getRecentSearch()

        with(binding) {

            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setRV() {
        with(binding) {
            rvSearch.adapter = searchAdapter
            rvRecentSearch.adapter = recentSearchAdapter

            searchAdapter.onClickSearchItem = {
                it.id?.let { id ->
                    val savedCityDTO = SavedCityDTO(
                        name = it.name,
                        region = it.region,
                        country = it.country,
                        token = makeCityToken(id)
                    )
                    val recentSearchDTO = RecentSearchesDTO(
                        name = it.name,
                        region = it.region,
                        country = it.country
                    )
                    viewModel.insertRecentSearch(recentSearchDTO)
                    viewModel.insertSavedCity(savedCityDTO)
                    viewModel.setCityToken(makeCityToken(id))
                    if (!_isSelected) {
                        viewModel.setIsCitySelected(true)
                    }
                    findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToHomeFragment())
                }
            }

            recentSearchAdapter.onClickRecentItem = {
                editSearch.setText(it.name)
            }
        }
    }

    private fun setSearch() {
        with(binding) {
            editSearch.addTextChangedListener {
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(300)
                    val search = it.toString()
                    if (search.isNotEmpty()) {
                        if (rvSearch.isVisible) rvSearch.visible()
                        viewModel.searchCity(search)
                    } else {
                        viewModel.getRecentSearch()
                        rvSearch.gone()
                    }
                }
            }
        }
    }


}
package com.sycosoft.allsee.throwaway

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _harryPotterData: MutableStateFlow<String> = MutableStateFlow("No data")
    val harryPotterData: StateFlow<String> = _harryPotterData

    init {
        viewModelScope.launch {
            //getWizard("Fleamont", "Potter")

        }
    }

    private suspend fun getHouses() {
        _harryPotterData.value = RetrofitClient.wizardWordAPIService.getHouses().toString()
    }

    private suspend fun getWizard(firstName: String? = null, lastName: String? = null) {
        _harryPotterData.value = RetrofitClient.wizardWordAPIService.getWizards(firstName, lastName).toString()
    }

    private suspend fun submitFeedback(feedback: Feedback) {
        _harryPotterData.value = RetrofitClient.wizardWordAPIService.submitFeedback(feedback).toString()
    }
}
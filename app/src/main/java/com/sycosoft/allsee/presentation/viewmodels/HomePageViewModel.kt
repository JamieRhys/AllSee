package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {
    private val _personDetails = MutableStateFlow<Person?>(null)
    val personDetails: StateFlow<Person?> = _personDetails

    init {
        viewModelScope.launch {
            _personDetails.value = getPersonUseCase()
        }
    }
}
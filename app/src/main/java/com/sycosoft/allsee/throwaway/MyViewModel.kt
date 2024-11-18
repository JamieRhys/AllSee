package com.sycosoft.allsee.throwaway

import androidx.lifecycle.ViewModel
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(private val myRepository: AppRepository) : ViewModel() {
    init {
        println("Hello from my viewmodel")
    }
}
package com.sycosoft.allsee.throwaway

import javax.inject.Inject

class MyRepository @Inject constructor(
    private val apiService: MyApiService
){
    init {
        println("Hello from my repository")
    }

    suspend fun getData() = apiService.getData()
}
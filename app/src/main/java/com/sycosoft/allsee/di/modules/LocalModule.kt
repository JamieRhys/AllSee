package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.local.CryptoManager
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
object LocalModule {
    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager = CryptoManager()
}
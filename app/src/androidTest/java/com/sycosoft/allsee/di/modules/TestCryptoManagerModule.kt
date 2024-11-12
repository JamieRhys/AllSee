package com.sycosoft.allsee.di.modules

import dagger.Module
import dagger.Provides
import com.sycosoft.allsee.data.local.CryptoManager

@Module
class TestCryptoManagerModule {
    @Provides
    fun provideCryptoManager(): CryptoManager = CryptoManager()
}
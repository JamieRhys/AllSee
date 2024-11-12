package com.sycosoft.allsee.di.components

import com.sycosoft.allsee.data.local.CryptoManagerTest
import com.sycosoft.allsee.di.modules.TestCryptoManagerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestCryptoManagerModule::class,
    ]
)
interface TestCryptoManagerComponent {
    fun inject(test: CryptoManagerTest)
}
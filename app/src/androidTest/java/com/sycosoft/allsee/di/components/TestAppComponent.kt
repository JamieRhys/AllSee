package com.sycosoft.allsee.di.components

import com.sycosoft.allsee.data.remote.client.RetrofitClientTest
import com.sycosoft.allsee.di.modules.TestApplicationModule
import com.sycosoft.allsee.di.modules.TestNetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestApplicationModule::class,
        TestNetworkModule::class,
    ]
)
interface TestAppComponent {
    fun inject(test: RetrofitClientTest)
}
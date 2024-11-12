package com.sycosoft.allsee.di.components

import com.sycosoft.allsee.MainActivity
import com.sycosoft.allsee.di.modules.ContextModule
import com.sycosoft.allsee.di.modules.LocalModule
import com.sycosoft.allsee.di.modules.NetworkModule
import com.sycosoft.allsee.di.modules.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ContextModule::class,
        LocalModule::class,
        NetworkModule::class,
        RepositoryModule::class,
    ]
)
@Singleton
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}
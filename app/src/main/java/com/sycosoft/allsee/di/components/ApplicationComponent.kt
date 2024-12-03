package com.sycosoft.allsee.di.components

import android.app.Application
import com.sycosoft.allsee.presentation.AllSeeApplication
import com.sycosoft.allsee.di.modules.ContextModule
import com.sycosoft.allsee.di.modules.LocalModule
import com.sycosoft.allsee.di.modules.MainActivityModule
import com.sycosoft.allsee.di.modules.NetworkModule
import com.sycosoft.allsee.di.modules.RepositoryModule
import com.sycosoft.allsee.di.modules.UseCaseModule
import com.sycosoft.allsee.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        LocalModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        MainActivityModule::class,
        UseCaseModule::class,
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<AllSeeApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
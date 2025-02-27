package com.sycosoft.allsee.di.components

import android.app.Application
import com.sycosoft.allsee.MainActivityTest
import com.sycosoft.allsee.di.modules.ContextModule
import com.sycosoft.allsee.di.modules.MainActivityModule
import com.sycosoft.allsee.di.modules.MockRepositoryModule
import com.sycosoft.allsee.di.modules.NetworkModule
import com.sycosoft.allsee.di.modules.UseCaseModule
import com.sycosoft.allsee.di.modules.ViewModelModule
import com.sycosoft.allsee.presentation.AllSeeApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/** A Dagger Component that serves as the central dependency injection graph for the application.
 * It provides the required dependencies for each module.
 */
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        NetworkModule::class,
        MockRepositoryModule::class,
        ViewModelModule::class,
        MainActivityModule::class,
        UseCaseModule::class,
    ]
)
@Singleton
interface MockApplicationComponent : AndroidInjector<AllSeeApplication> {

    fun inject(mainActivityTest: MainActivityTest)

    /** A Factory interface to create an instance of the [MockApplicationComponent].
     * Allows passing an Application instance to be used in the dependency injection graph.
     */
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): MockApplicationComponent
    }
}
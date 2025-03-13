package com.sycosoft.allsee.di.components

import android.app.Application
import com.sycosoft.allsee.di.modules.AppUseCaseModule
import com.sycosoft.allsee.di.modules.ContextModule
import com.sycosoft.allsee.di.modules.LocalModule
import com.sycosoft.allsee.di.modules.MainActivityModule
import com.sycosoft.allsee.di.modules.NetworkModule
import com.sycosoft.allsee.di.modules.RepositoryModule
import com.sycosoft.allsee.di.modules.ViewModelModule
import com.sycosoft.allsee.presentation.AllSeeApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import uk.co.jaffakree.allsee.domain.di.DomainUseCaseModule
import javax.inject.Singleton

/** A Dagger Component that serves as the central dependency injection graph for the application.
 * It provides the required dependencies for each module.
 */
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        LocalModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        MainActivityModule::class,
        AppUseCaseModule::class,
        DomainUseCaseModule::class
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<AllSeeApplication> {

    /** A Factory interface to create an instance of the [ApplicationComponent].
     * Allows passing an Application instance to be used in the dependency injection graph.
     */
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
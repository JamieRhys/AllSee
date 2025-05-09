package uk.co.jaffakree.allsee.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import uk.co.jaffakree.allsee.di.modules.ContextModule
import uk.co.jaffakree.allsee.di.modules.MainActivityModule
import uk.co.jaffakree.allsee.di.modules.ViewModelModule
import uk.co.jaffakree.allsee.presentation.AllSeeApplication
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        MainActivityModule::class,
        ViewModelModule::class,
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<AllSeeApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
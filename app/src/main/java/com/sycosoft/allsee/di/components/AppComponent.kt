package com.sycosoft.allsee.di.components

import com.sycosoft.allsee.di.modules.AppModule
import com.sycosoft.allsee.di.modules.LocalModule
import com.sycosoft.allsee.di.modules.NetworkModule
import com.sycosoft.allsee.di.modules.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
//@Component(modules = [
//    AppModule::class,
//    LocalModule::class,
//    RepositoryModule::class,
//    NetworkModule::class,
//])
@Component
interface AppComponent {
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//        fun build(): AppComponent
//    }
//
//    fun inject(viewModel: AccountAccessPageViewModel)
}
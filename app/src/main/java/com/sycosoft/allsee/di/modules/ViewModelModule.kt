package com.sycosoft.allsee.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sycosoft.allsee.presentation.viewmodels.AccountAccessPageViewModel
import uk.co.jaffakree.allsee.feature_accountdetails.viewmodel.AccountDetailsPageViewModel
import com.sycosoft.allsee.presentation.viewmodels.HomePageViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @MapKey
    @Retention(RUNTIME)
    annotation class ViewModelKey(val value: KClass<out ViewModel>)

    companion object {

        @Provides
        @Singleton
        fun viewModelFactory(
            viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    viewModels.entries
                        .first { (key, _) -> modelClass.isAssignableFrom(key) }
                        .value
                        .get() as T
            }
    }

    @Binds
    @IntoMap
    @ViewModelKey(AccountAccessPageViewModel::class)
    abstract fun bindAccountAccessPageViewModel(viewModel: AccountAccessPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountDetailsPageViewModel::class)
    abstract fun bindAccountDetailsPageViewModel(viewModel: AccountDetailsPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    abstract fun bindHomePageViewModel(viewModel: HomePageViewModel): ViewModel
}

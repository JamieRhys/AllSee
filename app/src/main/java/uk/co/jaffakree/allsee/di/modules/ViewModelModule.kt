package uk.co.jaffakree.allsee.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import uk.co.jaffakree.allsee.login.presentation.viewmodel.LoginPageViewModel
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
    @ViewModelKey(LoginPageViewModel::class)
    abstract fun bindLoginPageViewModel(viewModel: LoginPageViewModel): ViewModel
}
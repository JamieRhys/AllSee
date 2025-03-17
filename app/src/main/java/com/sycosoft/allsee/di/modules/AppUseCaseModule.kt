package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.presentation.usecases.GetNameAndAccountTypeUseCase
import dagger.Module
import dagger.Provides

@Module
class AppUseCaseModule {
    @Provides
    fun provideGetNameAndAccountTypeUseCase(): GetNameAndAccountTypeUseCase =
        GetNameAndAccountTypeUseCase()
}
package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.domain.repository.AppRepository
import com.sycosoft.allsee.domain.usecases.GetAccountHolderUseCase
import com.sycosoft.allsee.domain.usecases.GetNameAndAccountTypeUseCase
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import com.sycosoft.allsee.domain.usecases.SaveTokenUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetAccountHolderUseCase(repository: AppRepository): GetAccountHolderUseCase =
        GetAccountHolderUseCase(repository)

    @Provides
    fun provideGetNameAndAccountTypeUseCase(): GetNameAndAccountTypeUseCase =
        GetNameAndAccountTypeUseCase()

    @Provides
    fun provideGetPersonUseCase(repository: AppRepository): GetPersonUseCase =
        GetPersonUseCase(repository)

    @Provides
    fun provideSaveTokenUseCase(repository: AppRepository): SaveTokenUseCase =
        SaveTokenUseCase(repository)
}
package uk.co.jaffakree.allsee.domain.di

import dagger.Module
import dagger.Provides
import uk.co.jaffakree.allsee.domain.repository.AppRepository
import uk.co.jaffakree.allsee.domain.usecases.GetAccountHolderUseCase
import uk.co.jaffakree.allsee.domain.usecases.GetBalanceUseCase
import uk.co.jaffakree.allsee.domain.usecases.GetPersonUseCase
import uk.co.jaffakree.allsee.domain.usecases.SaveTokenUseCase

@Module
class DomainUseCaseModule {

    @Provides
    fun provideGetAccountHolderUseCase(repository: AppRepository): GetAccountHolderUseCase =
        GetAccountHolderUseCase(repository)

    @Provides
    fun provideGetBalanceUseCase(repository: AppRepository): GetBalanceUseCase =
        GetBalanceUseCase(repository)

    @Provides
    fun provideGetPersonUseCase(repository: AppRepository): GetPersonUseCase =
        GetPersonUseCase(repository)

    @Provides
    fun provideSaveTokenUseCase(repository: AppRepository): SaveTokenUseCase =
        SaveTokenUseCase(repository)
}
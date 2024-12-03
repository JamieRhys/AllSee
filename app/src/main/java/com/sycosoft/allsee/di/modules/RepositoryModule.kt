package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.mappers.IdentityDtoMapper
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.data.repository.AppRepositoryImpl
import com.sycosoft.allsee.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAppRepository(
        apiService: StarlingBankApiService,
        tokenProvider: TokenProvider,
        identityDtoMapper: IdentityDtoMapper,
    ): AppRepository = AppRepositoryImpl(
        apiService = apiService,
        tokenProvider = tokenProvider,
        identityDtoMapper = identityDtoMapper,
    )
}
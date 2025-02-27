package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class MockRepositoryModule {

    @Provides
    @Singleton
    fun provideAppRepository(
    ): AppRepository = mockk()
}
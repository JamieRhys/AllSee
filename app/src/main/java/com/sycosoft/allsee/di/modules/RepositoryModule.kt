package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.repository.AppRepositoryImpl
import com.sycosoft.allsee.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import jakarta.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAppRepository(impl: AppRepositoryImpl): AppRepository
}
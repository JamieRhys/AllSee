package com.sycosoft.allsee.throwaway

import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideMyRepository(apiService: MyApiService): MyRepository = MyRepository(apiService)
}
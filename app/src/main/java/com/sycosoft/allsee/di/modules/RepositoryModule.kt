package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.local.database.dao.AccountsDao
import com.sycosoft.allsee.data.local.database.dao.BalanceDao
import com.sycosoft.allsee.data.local.database.dao.PersonDao
import com.sycosoft.allsee.domain.mappers.IdentityMapper
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.data.repository.AppRepositoryImpl
import com.sycosoft.allsee.domain.mappers.BalanceMapper
import com.sycosoft.allsee.domain.mappers.FullBalanceMapper
import com.sycosoft.allsee.domain.mappers.PersonMapper
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
        personDao: PersonDao,
        tokenProvider: TokenProvider,
        identityMapper: IdentityMapper,
        fullBalanceMapper: FullBalanceMapper,
        balanceMapper: BalanceMapper,
        personMapper: PersonMapper,
        accountsDao: AccountsDao,
        balanceDao: BalanceDao,
    ): AppRepository = AppRepositoryImpl(
        apiService = apiService,
        personDao = personDao,
        accountsDao = accountsDao,
        balanceDao = balanceDao,
        tokenProvider = tokenProvider,
        balanceMapper = balanceMapper,
        fullBalanceMapper = fullBalanceMapper,
        identityMapper = identityMapper,
        personMapper = personMapper,
    )
}
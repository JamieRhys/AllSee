package com.sycosoft.allsee.di.modules

import uk.co.jaffakree.allsee.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import uk.co.jaffakree.allsee.data.local.TokenProvider
import uk.co.jaffakree.allsee.data.local.database.dao.AccountsDao
import uk.co.jaffakree.allsee.data.local.database.dao.BalanceDao
import uk.co.jaffakree.allsee.data.local.database.dao.PersonDao
import uk.co.jaffakree.allsee.domain.repository.AppRepository
import uk.co.jaffakree.allsee.mappers.BalanceMapper
import uk.co.jaffakree.allsee.mappers.FullBalanceMapper
import uk.co.jaffakree.allsee.mappers.IdentityMapper
import uk.co.jaffakree.allsee.mappers.PersonMapper
import uk.co.jaffakree.allsee.remote.services.StarlingBankApiService
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
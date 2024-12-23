package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(): List<Account> = repository.getAccounts()
}
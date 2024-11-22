package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetAccountHolderUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(): AccountHolder = repository.getAccountHolder()
}
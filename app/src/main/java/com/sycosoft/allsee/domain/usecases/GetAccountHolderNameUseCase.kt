package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetAccountHolderNameUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): AccountHolderName = repository.getAccountHolderName()
}
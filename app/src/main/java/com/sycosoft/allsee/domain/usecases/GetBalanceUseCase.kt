package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.models.types.BalanceType
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(type: BalanceType) = repository.getBalance(type)
}
package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetFullBalanceUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke() = repository.getFullBalance()
}
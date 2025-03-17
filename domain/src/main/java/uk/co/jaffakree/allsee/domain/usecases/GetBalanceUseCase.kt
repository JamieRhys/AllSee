package uk.co.jaffakree.allsee.domain.usecases

import uk.co.jaffakree.allsee.domain.repository.AppRepository
import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(type: BalanceType) = repository.getBalance(type)
}
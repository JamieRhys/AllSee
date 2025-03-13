package uk.co.jaffakree.allsee.domain.usecases

import uk.co.jaffakree.allsee.domain.repository.AppRepository
import uk.co.jaffakree.allsee.domain.models.AccountHolder
import javax.inject.Inject

class GetAccountHolderUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(): AccountHolder = repository.getAccountHolder()
}
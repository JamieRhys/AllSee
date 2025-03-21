package uk.co.jaffakree.allsee.domain.usecases

import uk.co.jaffakree.allsee.domain.repository.AppRepository
import uk.co.jaffakree.allsee.domain.models.Account
import javax.inject.Inject

open class GetAccountsUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    open suspend operator fun invoke(): List<Account> = repository.getAccounts()
}
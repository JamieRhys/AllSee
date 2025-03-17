package uk.co.jaffakree.allsee.feature_accountdetails.usecases

import uk.co.jaffakree.allsee.domain.models.Account
import uk.co.jaffakree.allsee.domain.models.AccountDetails
import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.feature_accountdetails.mappers.AccountDetailsMapper
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor() {
    operator fun invoke(account: Account, person: Person): AccountDetails =
        AccountDetailsMapper.map(account, person)
}
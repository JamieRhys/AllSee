package com.sycosoft.allsee.presentation.usecases

import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.presentation.mappers.AccountDetailsMapper
import com.sycosoft.allsee.presentation.models.AccountDetails
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor() {
    operator fun invoke(account: Account, person: Person): AccountDetails =
        AccountDetailsMapper.map(account, person)
}
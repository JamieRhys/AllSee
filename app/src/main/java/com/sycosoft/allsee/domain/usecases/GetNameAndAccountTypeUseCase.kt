package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetNameAndAccountTypeUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): NameAndAccountType = repository.getNameAndAccountType()
}
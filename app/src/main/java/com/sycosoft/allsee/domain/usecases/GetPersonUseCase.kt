package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(): Person = repository.getPerson()
}
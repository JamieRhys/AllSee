package uk.co.jaffakree.allsee.domain.usecases

import uk.co.jaffakree.allsee.domain.repository.AppRepository
import uk.co.jaffakree.allsee.domain.models.Person
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(): Person = repository.getPerson()
}
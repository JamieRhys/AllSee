package uk.co.jaffakree.allsee.domain.usecases

import uk.co.jaffakree.allsee.domain.repository.AppRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(token: String) = repository.saveToken(token)
}
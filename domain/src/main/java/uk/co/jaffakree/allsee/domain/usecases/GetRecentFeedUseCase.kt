package uk.co.jaffakree.allsee.domain.usecases

import uk.co.jaffakree.allsee.domain.repository.AppRepository
import javax.inject.Inject

class GetRecentFeedUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke() = repository.getRecentFeed()
}
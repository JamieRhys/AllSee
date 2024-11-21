package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(token: String) = repository.saveToken(token)
}
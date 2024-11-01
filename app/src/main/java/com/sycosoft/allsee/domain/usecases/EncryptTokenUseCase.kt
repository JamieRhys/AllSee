package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.repository.AppRepository
import jakarta.inject.Inject
import java.io.File

class EncryptTokenUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val filesDir: File
) {
    operator fun invoke(token: String): Boolean = appRepository.encryptAccessToken(token = token, filesDir = filesDir)
}
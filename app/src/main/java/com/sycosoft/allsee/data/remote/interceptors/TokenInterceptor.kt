package com.sycosoft.allsee.data.remote.interceptors

import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.inject.Inject

/** The token interceptor is used to intercept outgoing HTTP requests and add the decrypted authorization header. */
class TokenInterceptor @Inject constructor(
    private val cryptoManager: CryptoManager,
    private val tokenProvider: TokenProvider,
) : Interceptor {
    // Called whenever a request is made to the API.
    override fun intercept(chain: Interceptor.Chain): Response {
        // Decrypt the token (done within a coroutine block as it's suspending)
        val decryptedToken = runBlocking {
            val encryptedToken = tokenProvider.getEncryptedToken()
            // Decrypt and convert to UTF-8
            cryptoManager.decrypt(encryptedToken)
                .toString(Charsets.UTF_8)

        }

        // Create a new request with the Authorization header containing the decrypted token
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $decryptedToken")
            .build()

        // Proceed with the request but with the added header and return response.
        return chain.proceed(request)
    }
}
package com.sycosoft.allsee.data.remote.interceptors

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.net.UnknownHostException

class ApiHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            // Grab the response from the API call.
            val response = chain.proceed(chain.request())

            if (!response.isSuccessful) {
                // If the response is not successful, as in it's not between ranges 200 to 300...
                val errorBody = parseErrorResponse(response.body?.string()!!)
                // Throw an APIException. This is automatically parsed into an ErrorResponse DTO.
                throw ApiException(errorBody)
            }

            // If it was successful, return the response which is automatically parsed into a DTO.
            response
        } catch(e: HttpException) {
            throw ApiException(parseErrorResponse(e.response()?.errorBody()?.string()!!))
        } catch(e: UnknownHostException) {
            throw ApiException(ErrorResponseDto(error = "no_internet", errorDescription = "Unable to contact server. Please check your internet connection."))
        }
    }

    private fun parseErrorResponse(jsonString: String): ErrorResponseDto {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<ErrorResponseDto> = moshi.adapter(ErrorResponseDto::class.java)
        val response = jsonAdapter.fromJson(jsonString)
        return response!!
    }
}
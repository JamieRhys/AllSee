package uk.co.jaffakree.allsee.remote.client

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.jaffakree.allsee.remote.interceptors.ApiHandlerInterceptor
import uk.co.jaffakree.allsee.remote.interceptors.TokenInterceptor
import uk.co.jaffakree.allsee.remote.services.StarlingBankApiService
import javax.inject.Inject
import javax.inject.Singleton

/** A Singleton class that provides a preconfigured Retrofit instance.
 *
 * @property baseUrl The URL for API requests
 * @property tokenInterceptor The interceptor to handle token-related authentication requests.
 * @property apiHandlerInterceptor The interceptor to handle API-related errors.
 */
@Singleton
class RetrofitClient @Inject constructor(
    private val baseUrl: String,
    private val tokenInterceptor: TokenInterceptor,
    private val apiHandlerInterceptor: ApiHandlerInterceptor,
) {
    // Configures an HTTP logging interceptor to log the body of the network request and responses.
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Configures an OkHttp Client incorporating the logging, token and api handler interceptors.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(tokenInterceptor)
        .addInterceptor(apiHandlerInterceptor)
        .build()

    // Configures Moshi for JSON serialisation/de-serialisation with Kotlin support.
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Lazily initialised Retrofit service instance for interacting with the Starling Bank API
    // Uses the configured OkHttp Client and Moshi converter for API requests.
    val starlingBankApiService: StarlingBankApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(StarlingBankApiService::class.java)
    }
}
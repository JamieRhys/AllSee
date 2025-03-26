package uk.co.jaffakree.allsee.remote.client

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.jaffakree.allsee.domain.models.types.AccountType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType
import uk.co.jaffakree.allsee.remote.interceptors.ApiHandlerInterceptor
import uk.co.jaffakree.allsee.remote.interceptors.TokenInterceptor
import uk.co.jaffakree.allsee.remote.services.StarlingBankApiService
import java.time.OffsetDateTime
import java.util.UUID
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
        .add(MoshiAdapter)
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

@Suppress("unused")
internal class MoshiAdapter {
    companion object {
        @FromJson
        fun toUUID(value: String): UUID = UUID.fromString(value)

        @ToJson
        fun fromUUID(value: UUID): String = value.toString()

        @FromJson
        fun toAccountType(value: String): AccountType = AccountType.valueOf(value)

        @ToJson
        fun fromAccountType(value: AccountType): String = value.toString()

        @FromJson
        fun toCurrencyType(value: String): CurrencyType = CurrencyType.valueOf(value)

        @ToJson
        fun fromCurrencyType(value: CurrencyType): String = value.toString()

        @FromJson
        fun toOffsetDateTime(value: String): OffsetDateTime = OffsetDateTime.parse(value)

        @ToJson
        fun fromOffsetDateTime(value: OffsetDateTime): String = value.toString()
    }
}
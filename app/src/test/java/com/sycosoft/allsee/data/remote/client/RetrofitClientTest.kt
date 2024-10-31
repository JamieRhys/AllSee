package com.sycosoft.allsee.data.remote.client

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sycosoft.allsee.data.remote.service.StarlingBankApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class RetrofitClientTest {
    private val errorResponseInvalidAccessToken = """{ "error": "invalid_token", "error_description": "Could not validate access token" }"""

    private val validResponseAccountHolder = """{ accountHolderUid": "123456789", "accountHolderType": "INDIVIDUAL" }"""
    private val validResponseAccountHolderName = """{ "accountHolderName": "John Doe" }"""

// region Setup and Teardown
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: StarlingBankApiService
    private lateinit var gson: Gson

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = RetrofitClient(mockWebServer.url("/").toString()).starlingBankApiService
        gson = Gson()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

// endregion
// region Tests
    // region Get Account Holder
    @Test
    fun whenGetAccountHolder_givenCorrectResponse_thenApiAccountHolderObjectReturned() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""{ "accountHolderUid": "123456789", "accountHolderType": "INDIVIDUAL" }""")
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getAccountHolder("Bearer testAccessToken")

        assertEquals("123456789", response.accountHolderUid)
        assertEquals("INDIVIDUAL", response.accountHolderType)
    }

    @Test
    fun whenGetAccountHolderName_givenCorrectResponse_thenApiReturnsError() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(errorResponseInvalidAccessToken)
            .setResponseCode(403)
        mockWebServer.enqueue(mockResponse)

        val exception = assertThrows(HttpException::class.java) {
            runBlocking {
                apiService.getAccountHolder("Bearer invalidAccessToken")
            }
        }

        assertEquals(403, exception.response()?.code())

        val errorBody = exception.response()?.errorBody()
        assertNotNull(errorBody)

        val errorBodyString = errorBody?.string()
        assertEquals(errorResponseInvalidAccessToken, errorBodyString)
    }

// endregion
// region Get Account Holder Name

    @Test
    fun whenGetAccountHolderName_givenCorrectResponse_thenApiAccountHolderNameObjectReturned() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(validResponseAccountHolderName)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getAccountHolderName("Bearer testAccessToken")

        assertEquals("John Doe", response.accountHolderName)
    }

    @Test
    fun whenGetAccountHolderName_givenInvalidAccessToken_thenApiReturnsError() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(403)
            .setBody(errorResponseInvalidAccessToken)
        mockWebServer.enqueue(mockResponse)

        // Call the API service method
        val exception = assertThrows(HttpException::class.java) {
            runBlocking {
                apiService.getAccountHolderName("Bearer invalidAccessToken")
            }
        }

        assertEquals(403, exception.response()?.code())

        val errorBody = exception.response()?.errorBody()

        assertNotNull(errorBody)
        val errorBodyString = errorBody?.string()
        assertNotNull(errorBodyString)

        val jsonObject = gson.fromJson(errorBodyString, JsonObject::class.java)
        assertEquals("invalid_token", jsonObject["error"].asString)
        assertEquals("Could not validate access token", jsonObject["error_description"].asString)
    }

    // endregion
// endregion
}
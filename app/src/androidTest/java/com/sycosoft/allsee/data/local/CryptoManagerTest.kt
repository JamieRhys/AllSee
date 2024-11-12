package com.sycosoft.allsee.data.local

import com.sycosoft.allsee.di.components.DaggerTestCryptoManagerComponent
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.security.InvalidAlgorithmParameterException
import javax.crypto.BadPaddingException
import javax.inject.Inject

class CryptoManagerTest {
    @Inject
    lateinit var cryptoManager: CryptoManager

    private val testInput = "Hello world".toByteArray()

    @Before
    fun setup() {
        val testComponent = DaggerTestCryptoManagerComponent.create()
        testComponent.inject(this)
    }

    @Test
    fun `Test Encryption and Decryption Methods`() {
        val outputStream = ByteArrayOutputStream()
        val encryptedData = cryptoManager.encrypt(testInput)
        assertNotNull(encryptedData)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val decryptedData = cryptoManager.decrypt(encryptedData)
        assertArrayEquals(testInput, decryptedData)
    }

    @Test(expected = BadPaddingException::class)
    fun testDecryptionWithTamperedData() {
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(testInput)

        val tamperedData = outputStream.toByteArray().apply {
            this[this.lastIndex] = (this[this.lastIndex] + 1).toByte()
        }

        val inputStream = ByteArrayInputStream(tamperedData)
        cryptoManager.decrypt(tamperedData)
    }

    @Test(expected = InvalidAlgorithmParameterException::class)
    fun testDecryptionWithIncorrectIv() {
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(testInput)

        val data = outputStream.toByteArray()
        val modifiedIv = ByteArray(data[0] + 1)
        val inputStream = ByteArrayInputStream(modifiedIv + data.drop(modifiedIv.size).toByteArray())

        cryptoManager.decrypt(modifiedIv)
    }

    @Test(expected = BadPaddingException::class)
    fun testDecryptCorruptedData() {
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(testInput)

        // Corrupt the encrypted data
        val corruptedData = outputStream.toByteArray().apply {
            this[this.size - 1] = (this.last() + 1).toByte()
        }
        val inputStream = ByteArrayInputStream(corruptedData)

        cryptoManager.decrypt(corruptedData) // Should throw a BadPaddingException
    }

    @Test
    fun testEmptyDataEncryptionAndDecryption() {
        val emptyData = ByteArray(0)
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(emptyData)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val decryptedData = cryptoManager.decrypt(emptyData)

        assertArrayEquals(emptyData, decryptedData)
    }

    @Test
    fun testEncryptionProducesDifferentOutputs() {
        val outputStream1 = ByteArrayOutputStream()
        val outputStream2 = ByteArrayOutputStream()

        cryptoManager.encrypt(testInput)
        cryptoManager.encrypt(testInput)

        // Since each encryption uses a different IV, encrypted outputs should differ
        assertNotEquals(outputStream1.toByteArray(), outputStream2.toByteArray())
    }
}


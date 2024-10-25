package com.sycosoft.allsee.data.local

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.security.InvalidAlgorithmParameterException
import javax.crypto.BadPaddingException

class CryptoManagerTest {
    private lateinit var cryptoManager: CryptoManager
    private val testInput = "Hello world".toByteArray()

    @Before
    fun setup() {
        cryptoManager = CryptoManager()
    }

    @Test
    fun testEncryptionAndDecryption() {
        val outputStream = ByteArrayOutputStream()
        val encryptedData = cryptoManager.encrypt(testInput, outputStream)
        assertNotNull(encryptedData)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val decryptedData = cryptoManager.decrypt(inputStream)
        assertArrayEquals(testInput, decryptedData)
    }

    @Test(expected = BadPaddingException::class)
    fun testDecryptionWithTamperedData() {
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(testInput, outputStream)

        val tamperedData = outputStream.toByteArray().apply {
            this[this.lastIndex] = (this[this.lastIndex] + 1).toByte()
        }

        val inputStream = ByteArrayInputStream(tamperedData)
        cryptoManager.decrypt(inputStream)
    }

    @Test(expected = InvalidAlgorithmParameterException::class)
    fun testDecryptionWithIncorrectIv() {
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(testInput, outputStream)

        val data = outputStream.toByteArray()
        val modifiedIv = ByteArray(data[0] + 1)
        val inputStream = ByteArrayInputStream(modifiedIv + data.drop(modifiedIv.size).toByteArray())

        cryptoManager.decrypt(inputStream)
    }

    @Test(expected = BadPaddingException::class)
    fun testDecryptCorruptedData() {
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(testInput, outputStream)

        // Corrupt the encrypted data
        val corruptedData = outputStream.toByteArray().apply {
            this[this.size - 1] = (this.last() + 1).toByte()
        }
        val inputStream = ByteArrayInputStream(corruptedData)

        cryptoManager.decrypt(inputStream) // Should throw a BadPaddingException
    }

    @Test
    fun testEmptyDataEncryptionAndDecryption() {
        val emptyData = ByteArray(0)
        val outputStream = ByteArrayOutputStream()
        cryptoManager.encrypt(emptyData, outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val decryptedData = cryptoManager.decrypt(inputStream)

        assertArrayEquals(emptyData, decryptedData)
    }

    @Test
    fun testEncryptionProducesDifferentOutputs() {
        val outputStream1 = ByteArrayOutputStream()
        val outputStream2 = ByteArrayOutputStream()

        cryptoManager.encrypt(testInput, outputStream1)
        cryptoManager.encrypt(testInput, outputStream2)

        // Since each encryption uses a different IV, encrypted outputs should differ
        assertNotEquals(outputStream1.toByteArray(), outputStream2.toByteArray())
    }
}
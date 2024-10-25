package com.sycosoft.allsee.data.local

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.EOFException
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoManager {

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val KEY_ALIAS = "token"
    }

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val encryptCipher: Cipher
        get() = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }

    private fun getDecryptCipher(iv: ByteArray): Cipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey = KeyGenerator.getInstance(ALGORITHM).apply {
        init(
            KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(BLOCK_MODE)
                .setEncryptionPaddings(PADDING)
                .setUserAuthenticationRequired(false)
                .build()
        )
    }.generateKey()

    fun encrypt(bytes: ByteArray, outputStream: OutputStream): ByteArray {
        val cipher = encryptCipher
        val iv = cipher.iv // Capture the IV after initialization

        val encryptedBytes = cipher.doFinal(bytes)
        outputStream.use {
            it.write(iv.size) // Write IV size
            it.write(iv) // Write IV data
            it.write(encryptedBytes.size) // Write encrypted data size
            it.write(encryptedBytes) // Write encrypted data
        }
        return encryptedBytes
    }

    fun decrypt(inputStream: InputStream): ByteArray = inputStream.use {
        // Read IV size and IV itself
        val ivSize = it.read()
        val iv = ByteArray(ivSize)
        it.readFully(iv)

        // Read encrypted data size and encrypted data
        val encryptedBytesSize = it.read()
        val encryptedBytes = ByteArray(encryptedBytesSize)
        it.readFully(encryptedBytes)

        // Decrypt using the correct IV
        getDecryptCipher(iv).doFinal(encryptedBytes)
    }

    // Extension function for reading the full array from the stream
    private fun InputStream.readFully(bytes: ByteArray) {
        var bytesRead: Int
        var totalBytesRead = 0
        while (totalBytesRead < bytes.size) {
            bytesRead = read(bytes, totalBytesRead, bytes.size - totalBytesRead)
            if (bytesRead == -1) throw EOFException("Unexpected end of input stream")
            totalBytesRead += bytesRead
        }
    }
}

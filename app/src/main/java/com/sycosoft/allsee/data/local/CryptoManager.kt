package com.sycosoft.allsee.data.local

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.ByteArrayOutputStream
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
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES // AES Encryption algorithm
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC // CBC block mode for the encryption
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7 // PKCS7 padding for the encryption
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING" // Full transformation string combining the algorithm, block mode and padding.
        private const val KEY_ALIAS = "token" // Encryption/Decryption key alias.
    }

    /** Initialises the [KeyStore] instance used to manage cryptographic keys securely */
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    /** Cipher used to encrypt the data */
    private val encryptCipher: Cipher get() = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, getKey())
    }

    /** Cipher to decrypt the data */
    private fun getDecryptCipher(iv: ByteArray): Cipher = Cipher.getInstance(TRANSFORMATION).apply {
        require(iv.size == 16) { "Invalid IV length ${iv.size}. IV must be 16 bytes."}
        init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
    }

    /** Retrieves the encryption key from the Android keystore. If it doesn't exist, it creates a new one. */
    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    /** The function that creates the key if it doesn't exist. */
    private fun createKey(): SecretKey = KeyGenerator.getInstance(ALGORITHM).apply {
        init(
            KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(BLOCK_MODE)
                .setEncryptionPaddings(PADDING)
                .setUserAuthenticationRequired(false)
                .build()
        )
    }.generateKey()

    /** Encrypts the given token into a byte array. */
    fun encrypt(token: String): ByteArray {
        val encryptedData = encrypt(token.toByteArray())
        return if (encryptedData.isNotEmpty()) {
            encryptedData
        } else {
            ByteArray(0)
        }
    }

    /** Encrypts the given token into a byte array. */
    fun encrypt(bytes: ByteArray): ByteArray {
        val cipher = encryptCipher
        val iv = cipher.iv

        val encryptedBytes = cipher.doFinal(bytes)

        return ByteArray(iv.size + encryptedBytes.size).apply {
            System.arraycopy(iv, 0, this, 0, iv.size)
            System.arraycopy(encryptedBytes, 0, this, iv.size, encryptedBytes.size)
        }
    }

    /** Decrypts the given byte array that contains both the IV and encrypted data. */
    fun decrypt(encryptedDataWithIv: ByteArray): ByteArray {
        val iv = encryptedDataWithIv.copyOfRange(0, 16)
        val encryptedData = encryptedDataWithIv.copyOfRange(16, encryptedDataWithIv.size)

        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }

        return cipher.doFinal(encryptedData)
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
package br.testesoftdesign.utils

import java.security.MessageDigest

object Enviroment {
    const val publicKey: String = "ad9c2fa2fa52e0cbfeaf826924782934"
    const val ts: Int = 1
    private const val privateKey = "336515f6dd3384a1e3d3c7cf16bcc6c20d25cae7"

    fun hash(): String {
        val bytes =
            MessageDigest.getInstance("MD5").digest("$ts$privateKey$publicKey".toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
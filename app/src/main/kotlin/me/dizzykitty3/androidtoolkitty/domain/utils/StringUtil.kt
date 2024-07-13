package me.dizzykitty3.androidtoolkitty.domain.utils

import androidx.annotation.CheckResult
import java.time.LocalTime
import java.util.Locale

object StringUtil {
    fun greeting(): String = when (LocalTime.now().hour) {
        in 6..11 -> "Good morning"
        in 12..18 -> "Good afternoon"
        in 19..22 -> "Good evening"
        else -> "Good night"
    }

    /**
     * Drop spaces, including full-width ones.
     */
    fun String.dropSpaces(): String = this.replace("\\s".toRegex(), "")

    /**
     * Drop spaces, including full-width ones. Then lowercase.
     */
    fun String.dropSpacesAndLowercase(): String = this.dropSpaces().lowercase()

    @Throws(IllegalArgumentException::class)
    fun unicodeToCharacter(unicode: String): String {
        val length = unicode.length
        require(length % 4 == 0) { "The length of the input is not a multiple of 4" }

        try {
            val stringBuilder = StringBuilder()
            var i = 0
            while (i < length) {
                val hexValue = unicode.substring(i, i + 4)
                val decimalValue = hexValue.toInt(16)
                stringBuilder.append(decimalValue.toChar())
                i += 4
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid Unicode string format: ", e)
        }
    }

    @Throws(IllegalArgumentException::class)
    fun characterToUnicode(characters: String): String {
        require(characters.isNotEmpty()) { "Input string is empty" }
        val stringBuilder = StringBuilder()
        for (char in characters) {
            val unicodeValue = char.code
            val hexString = unicodeValue.toString(16).padStart(4, '0')
            stringBuilder.append(hexString)
        }
        return stringBuilder.toString()
    }

    val sysLocale: String
        @CheckResult get() = Locale.getDefault().toString()

    @Suppress("MemberVisibilityCanBePrivate")
    val sysLangSupported: Boolean
        @CheckResult get() = sysLocale.contains(("en|Hans|zh_CN|zh_SG|ja").toRegex())

    val sysLangNotSupported: Boolean
        @CheckResult get() = !sysLangSupported

    val sysLangCJK: Boolean
        @CheckResult get() = sysLocale.contains(("Hans|Hant|zh|ja|ko").toRegex())

    fun String.toASCII(): String = this.map { it.code }.joinToString(", ")

    /**
     * Allows for letters, numbers, or underscores.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun String.isValidUsername(): Boolean = this.matches(("^[a-zA-Z0-9_]*$").toRegex())

    fun String.isInvalidUsername(): Boolean = !this.isValidUsername()
}
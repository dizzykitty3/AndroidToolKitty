package me.dizzykitty3.androidtoolkitty.foundation.utils

import java.time.LocalTime

object TString {
    @JvmStatic
    fun greeting(): String {
        val currentTime = LocalTime.now()
        return when (currentTime.hour) {
            in 6..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            else -> "Good evening"
        }
    }

    /**
     * Drop spaces, including full-width ones.
     */
    @JvmStatic
    fun dropSpaces(inputString: String): String {
        return inputString.trim().replace("\\s".toRegex(), "").lowercase()
    }

    @JvmStatic
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

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun characterToUnicode(characters: String): String {
        require(characters.isNotEmpty()) { "Input string is empty" }
        val stringBuilder = StringBuilder()
        for (char in characters) {
            val unicodeValue = char.code
            val hexString = unicodeValue.toString(16).padStart(4, '0') // 转换成十六进制并确保长度为4
            stringBuilder.append(hexString)
        }
        return stringBuilder.toString()
    }

}
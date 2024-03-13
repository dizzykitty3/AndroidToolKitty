package me.dizzykitty3.androidtoolkitty.common.util

import android.util.Log
import java.time.LocalTime

object StringUtils {
    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun debugLog(message: String) {
        Log.d("me.dizzykitty3.androidtoolkitty", message)
    }

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
     * drop spaces, including full-width ones
     */
    @JvmStatic
    fun dropSpaces(inputString: String): String {
        return inputString.trim().replace("\\s".toRegex(), "").lowercase()
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun convertUnicodeToCharacter(unicode: String): String {
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
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid Unicode string format: ", e)
        }
    }
}
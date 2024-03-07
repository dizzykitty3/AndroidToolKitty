package me.dizzykitty3.androidtoolkitty.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

object TextUtils {
    private const val COM_CN = ".com.cn"

    @JvmStatic
    fun greeting(): String {
        val currentTime = LocalTime.now()
        return when (currentTime.hour) {
            in 6..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            else -> "Good evening"
        }
    }

    @JvmStatic
    fun calculateDaysPassed(): Long {
        return calculateDaysFromStartOfYear(LocalDate.now())
    }

    @JvmStatic
    fun calculateTotalDaysInYear(): Long {
        val currentDate = LocalDate.now()
        return calculateDaysFromStartOfYear(LocalDate.of(currentDate.year, 12, 31)) + 1
    }

    private fun calculateDaysFromStartOfYear(endDate: LocalDate): Long {
        val startOfYear = LocalDate.of(endDate.year, 1, 1)
        return startOfYear.until(endDate, ChronoUnit.DAYS)
    }

    @JvmStatic
    fun calculateYearProgress(): Float {
        return calculateDaysPassed().toFloat() / calculateTotalDaysInYear().toFloat()
    }

    @JvmStatic
    fun displayYearProgressPercentage(progress: Float): String {
        return (progress * 100).toString().substring(0, 4)
    }


    @JvmStatic
    fun dropSpaces(inputString: String): String {
        // drop spaces (including full-width)
        val result = inputString.trim().replace("\\s".toRegex(), "").lowercase()
        Utils.debugLog("string process: input = $inputString, output = $result")
        return result
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun processUrl(inputUrl: String): String {
        val prefix = "https://"
        if (inputUrl.contains(".")) return "$prefix$inputUrl"

        val suffixMap = mapOf(
            // .bg
            "remove" to ".bg",
            // .cn
            "feishu" to ".cn",
            "52pojie" to "cn",
            "360" to "cn",
            // .co.jp
            "rakuten" to ".co.jp",
            "dmm" to ".co.jp",
            // .co.ar
            "mercadolibre" to ".co.ar",
            // .com.cn
            "autohome" to COM_CN, "zol" to COM_CN, "pconline" to COM_CN,
            // .co.uk
            "dailymail" to "co.uk", "bbc" to "co.uk",
            // .ee
            "linktr" to ".ee",
            // .ir
            "shaparak" to ".ir",
            // .jp
            "livedoor" to ".jp", "nicovideo" to ".jp",
            // .la
            "hitomi" to ".la",
            // .net
            "csdn" to ".net",
            "pixiv" to ".net",
            "atlassian" to ".net",
            "cnki" to ".net",
            "doubleclick" to ".net",
            "speedtest" to ".net",
            "researchgate" to ".net",
            "behance" to ".net",
            "ali213" to ".net",
            "savefrom" to ".net",
            "cloudfront" to ".net",
            "bytedance" to ".net",
            "nhentai" to ".net",
            "daum" to ".net",
            "animeflv" to ".net",
            "jb51" to ".net",
            "manatoki215" to ".net",
            // .me
            "line" to ".me",
            // .mx
            "yts" to ".mx",
            // .nz
            "mega" to ".nz",
            // .org
            "wikipedia" to ".org",
            "telegram" to ".org",
            "archive" to ".org",
            "mozilla" to ".org",
            "e-hentai" to ".org",
            "greasyfork" to ".org",
            "coursera" to ".org",
            "craigslist" to ".org",
            // .ru
            "yandex" to ".ru",
            "mail" to ".ru",
            "dzen" to ".ru",
            "avito" to ".ru",
            "ok" to ".ru",
            "ozon" to ".ru",
            "wildberries" to ".ru",
            "gosulugi" to ".ru",
            "ya" to ".ru",
            // .so
            "notion" to ".so",
            // .to
            "zoro" to ".to",
            "1337x" to ".to",
            // .tv
            "twitch" to ".tv",
            "jable" to ".tv",
            // .us
            "zoom" to ".us",
            // .wiki
            "namu" to ".wiki",
        )

        val suffix = suffixMap[inputUrl] ?: ".com"
        return "$prefix$inputUrl$suffix"
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
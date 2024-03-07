package me.dizzykitty3.androidtoolkitty.util

import me.dizzykitty3.androidtoolkitty.util.Utils.debugLog
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

object TextUtils {
    private const val COM_CN = ".com.cn"
    private const val NET = ".net"
    private const val ORG = ".org"
    private const val RU = ".ru"

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

    /**
     * drop spaces, including full-width ones
     */
    @JvmStatic
    fun dropSpaces(inputString: String): String {
        return inputString.trim().replace("\\s".toRegex(), "").lowercase()
    }

    @JvmStatic
    fun processUrl(inputUrl: String): String {
        val prefix = "https://"
        if (inputUrl.contains(".")) {
            debugLog("input url: $inputUrl")
            return "$prefix$inputUrl"
        }
        val suffix = getUrlSuffix(inputUrl)
        debugLog(if (suffix == ".com") "suffix = com, input url: $inputUrl" else "suffix = $suffix")
        return "$prefix$inputUrl$suffix"
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun getUrlSuffix(urlInput: String): String {
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
            "csdn" to NET,
            "pixiv" to NET,
            "atlassian" to NET,
            "cnki" to NET,
            "doubleclick" to NET,
            "speedtest" to NET,
            "researchgate" to NET,
            "behance" to NET,
            "ali213" to NET,
            "savefrom" to NET,
            "cloudfront" to NET,
            "bytedance" to NET,
            "nhentai" to NET,
            "daum" to NET,
            "animeflv" to NET,
            "jb51" to NET,
            "manatoki215" to NET,
            // .me
            "line" to ".me",
            // .mx
            "yts" to ".mx",
            // .nz
            "mega" to ".nz",
            // .org
            "wikipedia" to ORG,
            "telegram" to ORG,
            "archive" to ORG,
            "mozilla" to ORG,
            "e-hentai" to ORG,
            "greasyfork" to ORG,
            "coursera" to ORG,
            "craigslist" to ORG,
            // .ru
            "yandex" to RU,
            "mail" to RU,
            "dzen" to RU,
            "avito" to RU,
            "ok" to RU,
            "ozon" to RU,
            "wildberries" to RU,
            "gosulugi" to RU,
            "ya" to RU,
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
        return suffixMap[dropSpaces(urlInput)] ?: ".com"
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
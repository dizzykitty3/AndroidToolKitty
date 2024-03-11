package me.dizzykitty3.androidtoolkitty.util

import me.dizzykitty3.androidtoolkitty.util.Utils.debugLog
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

object TextUtils {
    private const val BG = ".bg"
    private const val CN = ".cn"
    private const val CO_AR = ".co.ar"
    private const val CO_JP = ".co.jp"
    private const val CO_UK = ".co.uk"
    private const val COM = ".com"
    private const val COM_CN = ".com.cn"
    private const val EE = ".ee"
    private const val IR = ".ir"
    private const val JP = ".jp"
    private const val LA = ".la"
    private const val NET = ".net"
    private const val ME = ".me"
    private const val MX = ".mx"
    private const val NZ = ".nz"
    private const val ORG = ".org"
    private const val RU = ".ru"
    private const val SO = ".so"
    private const val TO = ".to"
    private const val TV = ".tv"
    private const val US = ".us"
    private const val WIKI = ".wiki"

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
    fun processUrl(urlInput: String): String {
        val prefix = "https://"
        val suffix = getUrlSuffix(urlInput)
        debugLog(if (suffix == COM) "suffix = com, input url: $urlInput" else "suffix = $suffix")
        return "$prefix$urlInput$suffix"
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun getUrlSuffix(urlInput: String): String {
        if (urlInput.contains(".")) {
            return ""
        }
        val suffixMap = mapOf(
            "remove" to BG,
            "feishu" to CN,
            "52pojie" to CN,
            "360" to CN,
            "mercadolibre" to CO_AR,
            "rakuten" to CO_JP,
            "dmm" to CO_JP,
            "autohome" to COM_CN,
            "zol" to COM_CN,
            "pconline" to COM_CN,
            "dailymail" to CO_UK,
            "bbc" to CO_UK,
            "linktr" to EE,
            "shaparak" to IR,
            "livedoor" to JP,
            "nicovideo" to JP,
            "hitomi" to LA,
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
            "line" to ME,
            "yts" to MX,
            "mega" to NZ,
            "wikipedia" to ORG,
            "telegram" to ORG,
            "archive" to ORG,
            "mozilla" to ORG,
            "e-hentai" to ORG,
            "greasyfork" to ORG,
            "coursera" to ORG,
            "craigslist" to ORG,
            "yandex" to RU,
            "mail" to RU,
            "dzen" to RU,
            "avito" to RU,
            "ok" to RU,
            "ozon" to RU,
            "wildberries" to RU,
            "gosulugi" to RU,
            "ya" to RU,
            "notion" to SO,
            "zoro" to TO,
            "1337x" to TO,
            "twitch" to TV,
            "jable" to TV,
            "zoom" to US,
            "namu" to WIKI,
        )
        return suffixMap[dropSpaces(urlInput)] ?: COM
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
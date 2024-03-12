package me.dizzykitty3.androidtoolkitty.util

import me.dizzykitty3.androidtoolkitty.Actions

object UrlUtils {
    private const val HTTPS = "https://"
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
    fun processUrl(urlInput: String): String {
        val suffix = getUrlSuffix(urlInput)
        Actions.debugLog(
            if (suffix == COM)
                "suffix = com, input url: $urlInput"
            else
                "suffix = $suffix"
        )
        return "$HTTPS$urlInput$suffix"
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
        return suffixMap[StringUtils.dropSpaces(urlInput)] ?: COM
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun getProfilePrefix(platformIndex: Int): String {
        return when (platformIndex) {
            1 -> "search.bilibili.com/upuser?keyword="
            2 -> "github.com/"
            3 -> "pixiv.net/artworks/"
            4 -> "pixiv.net/users/"
            5 -> "v2ex.com/member/"
            6 -> "weibo.com/n/"
            7 -> "x.com/"
            8 -> "youtube.com/@"
            else -> ""
        }
    }
}
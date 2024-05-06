package me.dizzykitty3.androidtoolkitty.foundation.util

import android.util.Log
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.const.BG
import me.dizzykitty3.androidtoolkitty.foundation.const.CN
import me.dizzykitty3.androidtoolkitty.foundation.const.COM
import me.dizzykitty3.androidtoolkitty.foundation.const.COM_CN
import me.dizzykitty3.androidtoolkitty.foundation.const.CO_AR
import me.dizzykitty3.androidtoolkitty.foundation.const.CO_JP
import me.dizzykitty3.androidtoolkitty.foundation.const.CO_UK
import me.dizzykitty3.androidtoolkitty.foundation.const.EE
import me.dizzykitty3.androidtoolkitty.foundation.const.HTTPS
import me.dizzykitty3.androidtoolkitty.foundation.const.IR
import me.dizzykitty3.androidtoolkitty.foundation.const.JP
import me.dizzykitty3.androidtoolkitty.foundation.const.LA
import me.dizzykitty3.androidtoolkitty.foundation.const.ME
import me.dizzykitty3.androidtoolkitty.foundation.const.MX
import me.dizzykitty3.androidtoolkitty.foundation.const.NET
import me.dizzykitty3.androidtoolkitty.foundation.const.NZ
import me.dizzykitty3.androidtoolkitty.foundation.const.ORG
import me.dizzykitty3.androidtoolkitty.foundation.const.RU
import me.dizzykitty3.androidtoolkitty.foundation.const.SO
import me.dizzykitty3.androidtoolkitty.foundation.const.TO
import me.dizzykitty3.androidtoolkitty.foundation.const.TV
import me.dizzykitty3.androidtoolkitty.foundation.const.US
import me.dizzykitty3.androidtoolkitty.foundation.const.WIKI

object UrlUtil {
    private const val TAG = "UrlUtil"

    /**
     * Adding the appropriate suffix and returning the full URL.
     */
    fun toFullUrl(urlInput: String): String {
        val suffix = urlSuffix(urlInput)
        Log.d(TAG, if (suffix == COM) "suffix = com, input url: $urlInput" else "suffix = $suffix")
        return "$HTTPS$urlInput$suffix"
    }

    fun urlSuffix(urlInput: String): String {
        if (urlInput.contains(".")) return ""

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

        return suffixMap[StringUtil.dropSpaces(urlInput)] ?: COM
    }

    enum class Platform(val prefix: String, val nameResId: Int) {
        BILIBILI_SEARCH("m.bilibili.com/search?keyword=", R.string.bilibili_search),
        BILIBILI_USER("space.bilibili.com/", R.string.bilibili_user_id),
        FACEBOOK("facebook.com/", R.string.facebook),
        FANBOX(".fanbox.cc", R.string.fanbox), // e.g. username.fanbox.cc
        FANTIA("fantia.jp/", R.string.fantia),
        GITHUB("github.com/", R.string.github),
        INSTAGRAM("instagram.com/", R.string.instagram),
        PATREON("patreon.com/", R.string.patreon),
        PIXIV_ARTWORK("pixiv.net/artworks/", R.string.pixiv_artwork_id),
        PIXIV_USER("pixiv.net/users/", R.string.pixiv_user_id),
        PIXIV_USER_CUSTOM_URL("pixiv.me/", R.string.pixiv_user_custom_url),
        PLURK("plurk.com/", R.string.plurk),
        STEAM_USER_CUSTOM_URL("steamcommunity.com/id/", R.string.steam_user_custom_url),
        TWITCH("twitch.tv/", R.string.twitch),
        V2EX("v2ex.com/member/", R.string.v2ex),
        WEIBO("weibo.com/n/", R.string.weibo),
        X("x.com/", R.string.x),
        YOUTUBE("youtube.com/@", R.string.youtube_id),
        PLATFORM_NOT_ADDED_YET("", R.string.platform_not_added_yet)
    }

    /**
     * @return the profile prefix associated with the given platform.
     */
    fun profilePrefix(platform: Platform): String = platform.prefix
}
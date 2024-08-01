package me.dizzykitty3.androidtoolkitty.utils

import androidx.annotation.StringRes
import me.dizzykitty3.androidtoolkitty.BG
import me.dizzykitty3.androidtoolkitty.CN
import me.dizzykitty3.androidtoolkitty.COM
import me.dizzykitty3.androidtoolkitty.COM_CN
import me.dizzykitty3.androidtoolkitty.CO_AR
import me.dizzykitty3.androidtoolkitty.CO_JP
import me.dizzykitty3.androidtoolkitty.CO_UK
import me.dizzykitty3.androidtoolkitty.EE
import me.dizzykitty3.androidtoolkitty.HTTPS
import me.dizzykitty3.androidtoolkitty.IR
import me.dizzykitty3.androidtoolkitty.JP
import me.dizzykitty3.androidtoolkitty.LA
import me.dizzykitty3.androidtoolkitty.ME
import me.dizzykitty3.androidtoolkitty.MX
import me.dizzykitty3.androidtoolkitty.NET
import me.dizzykitty3.androidtoolkitty.NZ
import me.dizzykitty3.androidtoolkitty.ORG
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.RU
import me.dizzykitty3.androidtoolkitty.SO
import me.dizzykitty3.androidtoolkitty.TO
import me.dizzykitty3.androidtoolkitty.TV
import me.dizzykitty3.androidtoolkitty.US
import me.dizzykitty3.androidtoolkitty.WIKI
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.dropSpaces
import timber.log.Timber

object URLUtil {
    /**
     * Adding the appropriate suffix and returning the full URL.
     */
    fun String.toFullURL(): String {
        val suffix = this.getSuffix()
        Timber.d(
            if (suffix == COM)
                "suffix = com, input url: $this"
            else
                "suffix = $suffix"
        )
        return if (this.contains(HTTPS)) "$this$suffix"
        else "$HTTPS$this$suffix"
    }

    fun String.getSuffix(): String {
        if (this.contains(".")) return ""
        val suffixMap = mapOf(
            "remove" to BG, "feishu" to CN, "52pojie" to CN, "360" to CN, "mercadolibre" to CO_AR,
            "rakuten" to CO_JP, "dmm" to CO_JP, "autohome" to COM_CN, "zol" to COM_CN,
            "pconline" to COM_CN, "dailymail" to CO_UK, "bbc" to CO_UK, "linktr" to EE,
            "shaparak" to IR, "livedoor" to JP, "nicovideo" to JP, "hitomi" to LA, "csdn" to NET,
            "pixiv" to NET, "atlassian" to NET, "cnki" to NET, "doubleclick" to NET,
            "speedtest" to NET, "researchgate" to NET, "behance" to NET, "ali213" to NET,
            "savefrom" to NET, "cloudfront" to NET, "bytedance" to NET, "nhentai" to NET,
            "daum" to NET, "animeflv" to NET, "jb51" to NET, "manatoki215" to NET, "line" to ME,
            "yts" to MX, "mega" to NZ, "wikipedia" to ORG, "telegram" to ORG, "archive" to ORG,
            "mozilla" to ORG, "e-hentai" to ORG, "greasyfork" to ORG, "coursera" to ORG,
            "craigslist" to ORG, "yandex" to RU, "mail" to RU, "dzen" to RU, "avito" to RU,
            "ok" to RU, "ozon" to RU, "wildberries" to RU, "gosulugi" to RU, "ya" to RU,
            "notion" to SO, "zoro" to TO, "1337x" to TO, "twitch" to TV, "jable" to TV,
            "zoom" to US, "namu" to WIKI
        )
        return suffixMap[this.dropSpaces().lowercase()] ?: COM
    }

    /**
     * @see me.dizzykitty3.androidtoolkitty.home.toSocialMediaFullURL
     */
    enum class Platform(val prefix: String, @StringRes val platform: Int) {
        ARTSTATION("artstation.com/", R.string.artstation),
        BILIBILI_SEARCH("m.bilibili.com/search?keyword=", R.string.bilibili_search),
        BILIBILI_USER("space.bilibili.com/", R.string.bilibili_user_id),
        BLUESKY("bsky.app/profile/", R.string.bluesky),
        BOOTH(".booth.pm", R.string.booth),
        CARRD(".carrd.co", R.string.carrd),
        FACEBOOK("facebook.com/", R.string.facebook),
        FANBOX(".fanbox.cc", R.string.fanbox),
        FANSLY("fansly.com/", R.string.fansly),
        FANTIA("fantia.jp/", R.string.fantia),
        GITHUB("github.com/", R.string.github),
        INSTAGRAM("instagram.com/", R.string.instagram),
        KO_FI("ko-fi.com/", R.string.ko_fi),
        LINKEDIN_COMPANY("linkedin.com/company/", R.string.linkedin_company),
        LINKEDIN_USER("linkedin.com/in/", R.string.linkedin_user),
        LINKTREE("linktr.ee/", R.string.linktree),
        LIT_LINK("lit.link/", R.string.lit_link),
        MARSHMALLOW_QA("marshmallow-qa.com/", R.string.marshmallow_qa),
        MASTODON("mastodon.social/@", R.string.mastodon),
        MISSKEY("misskey.io/@", R.string.misskeyio),
        NOTE("note.com/", R.string.note),
        PATREON("patreon.com/", R.string.patreon),
        PAYPAL("paypal.me/", R.string.paypal),
        PINTEREST("pinterest.com/", R.string.pinterest),
        PIXIV_ARTWORK("pixiv.net/artworks/", R.string.pixiv_artwork_id),
        PIXIV_USER("pixiv.net/users/", R.string.pixiv_user_id),
        PIXIV_USER_CUSTOM_URL("pixiv.me/", R.string.pixiv_user_custom_url),
        PLURK("plurk.com/", R.string.plurk),
        POTOFU("potofu.me/", R.string.potofu),
        PROFCARD("profcard.info/u/", R.string.profcard),
        REDDIT_SUBREDDIT("reddit.com/r/", R.string.reddit_subreddit),
        SKEB("skeb.jp/@", R.string.skeb),
        SNAPCHAT("snapchat.com/add/", R.string.snapchat),
        STEAM_USER_CUSTOM_URL("steamcommunity.com/id/", R.string.steam_user_custom_url),
        TELEGRAM("t.me/", R.string.telegram),
        THREADS("threads.net/@", R.string.threads),
        TIKTOK("tiktok.com/@", R.string.tiktok),
        TUMBLR(".tumblr.com", R.string.tumblr),
        TWITCASTING("twitcasting.tv/c:", R.string.twitcasting),
        TWITCH("twitch.tv/", R.string.twitch),
        UNIFANS("app.unifans.io/c/", R.string.unifans),
        UNSPLASH("unsplash.com/@", R.string.unsplash),
        V2EX("v2ex.com/member/", R.string.v2ex),
        WAVEBOX("wavebox.me/wave/", R.string.wavebox),
        WEIBO("weibo.com/n/", R.string.weibo),
        X("x.com/", R.string.x),
        YOUTUBE_SEARCH("youtube.com/results?search_query=", R.string.youtube_serach),
        YOUTUBE_USER_ID("youtube.com/@", R.string.youtube_id)
    }
}
package me.dizzykitty3.androidtoolkitty.data.utils

import androidx.annotation.StringRes
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.BG
import me.dizzykitty3.androidtoolkitty.data.CN
import me.dizzykitty3.androidtoolkitty.data.COM
import me.dizzykitty3.androidtoolkitty.data.COM_CN
import me.dizzykitty3.androidtoolkitty.data.CO_AR
import me.dizzykitty3.androidtoolkitty.data.CO_JP
import me.dizzykitty3.androidtoolkitty.data.CO_UK
import me.dizzykitty3.androidtoolkitty.data.EE
import me.dizzykitty3.androidtoolkitty.data.HTTPS
import me.dizzykitty3.androidtoolkitty.data.IR
import me.dizzykitty3.androidtoolkitty.data.JP
import me.dizzykitty3.androidtoolkitty.data.LA
import me.dizzykitty3.androidtoolkitty.data.ME
import me.dizzykitty3.androidtoolkitty.data.MX
import me.dizzykitty3.androidtoolkitty.data.NET
import me.dizzykitty3.androidtoolkitty.data.NZ
import me.dizzykitty3.androidtoolkitty.data.ORG
import me.dizzykitty3.androidtoolkitty.data.RU
import me.dizzykitty3.androidtoolkitty.data.SO
import me.dizzykitty3.androidtoolkitty.data.TO
import me.dizzykitty3.androidtoolkitty.data.TV
import me.dizzykitty3.androidtoolkitty.data.US
import me.dizzykitty3.androidtoolkitty.data.WIKI
import me.dizzykitty3.androidtoolkitty.data.utils.StringUtil.dropSpacesAndLowercase
import timber.log.Timber

object URLUtil {
    /**
     * Adding the appropriate suffix and returning the full URL.
     */
    fun String.toFullURL(): String {
        val suffix = this.getSuffix()
        Timber.d(
            if (suffix == COM) "suffix = com, input url: $this"
            else "suffix = $suffix"
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
        return suffixMap[this.dropSpacesAndLowercase()] ?: COM
    }

    /**
     * @see me.dizzykitty3.androidtoolkitty.ui.view.home.toSocialMediaFullURL
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
        YOUTUBE("youtube.com/@", R.string.youtube_id)
    }
}
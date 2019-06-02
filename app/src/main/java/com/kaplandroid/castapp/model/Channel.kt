package com.kaplandroid.castapp.model

import android.util.Log
import com.kaplandroid.castapp.ChannelListDB

class Channel(val name: String, val keyword: String) {

    private var customLink: String? = null

    constructor(name: String, keyword: String, customLink: String) : this(name, keyword) {
        this.customLink = customLink
    }

    override fun toString(): String {
        return if (customLink == null)
            name
        else
            "$name *"
    }

    fun getLink(): String {
        val link: String = if (customLink != null) {
            customLink!!
        } else {
            "http://ch.canlitvlive.io/" + keyword + "/live.m3u8?tkn=" + ChannelListDB.token + "&tms=" + ChannelListDB.tms
        }
        Log.e("link", link)

        return link
    }
}
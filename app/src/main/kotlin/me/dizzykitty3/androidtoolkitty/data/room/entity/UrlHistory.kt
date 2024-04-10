package me.dizzykitty3.androidtoolkitty.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UrlHistory(
    @PrimaryKey val urlId: Int,
    @ColumnInfo(name = "url_input") val urlInput: String?,
)
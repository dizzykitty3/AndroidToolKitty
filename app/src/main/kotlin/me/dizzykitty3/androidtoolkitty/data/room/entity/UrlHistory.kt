package me.dizzykitty3.androidtoolkitty.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UrlHistory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val urlInput: String
)
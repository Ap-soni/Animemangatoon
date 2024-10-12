package com.example.animemangatoon.modelclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "webtoons")
data class Webtoon(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

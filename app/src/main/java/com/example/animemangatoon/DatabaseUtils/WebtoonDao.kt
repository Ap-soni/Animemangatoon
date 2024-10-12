package com.example.animemangatoon.DatabaseUtils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.animemangatoon.modelclass.Webtoon

@Dao
interface WebtoonDao {
    @Insert
    suspend fun insert(webtoon: Webtoon)

    @Query("SELECT * FROM webtoons WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Webtoon?

    @Query("SELECT * FROM webtoons")
    suspend fun getFavorites(): List<Webtoon>

    @Query("DELETE FROM webtoons WHERE id = :id")
    suspend fun deleteById(id: Int)
}


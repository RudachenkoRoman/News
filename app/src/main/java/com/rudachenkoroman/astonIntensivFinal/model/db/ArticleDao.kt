package com.rudachenkoroman.astonIntensivFinal.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rudachenkoroman.astonIntensivFinal.model.news.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>


    @Delete
    suspend fun delete(article: Article)
}
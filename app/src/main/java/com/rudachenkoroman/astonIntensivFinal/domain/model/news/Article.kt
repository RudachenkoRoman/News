package com.rudachenkoroman.astonIntensivFinal.domain.model.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)

data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    var urlToImage: String,
    var isFavorite: Boolean
) : Serializable
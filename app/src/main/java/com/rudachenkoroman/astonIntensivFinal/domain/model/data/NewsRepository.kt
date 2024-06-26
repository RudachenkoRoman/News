package com.rudachenkoroman.astonIntensivFinal.domain.model.data

import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.domain.model.db.ArticleDatabase

class NewsRepository (private val db: ArticleDatabase){

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}
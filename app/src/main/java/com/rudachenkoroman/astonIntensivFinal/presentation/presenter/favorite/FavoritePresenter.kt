package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.favorite

import com.rudachenkoroman.astonIntensivFinal.domain.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val dataSource: NewsDataSource
) : FavoriteHome.Presenter {
    fun getAll() {
        this.dataSource.getAllArticle(this)
    }
    fun saveArticle(article: Article) {
        this.dataSource.saveArticle(article)
    }
    fun deleteArticle(article: Article) {
        this.dataSource.deleteArticle(article)
    }
    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }
}
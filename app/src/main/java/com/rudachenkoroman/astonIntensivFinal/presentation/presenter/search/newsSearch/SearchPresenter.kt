package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.search.newsSearch

import com.rudachenkoroman.astonIntensivFinal.domain.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.ViewHome

class SearchPresenter(val view: ViewHome.View, private val dataSource: NewsDataSource) :
    SearchHome.Presenter {
    override fun search(term: String) {
        this.view.showProgressBar()
        this.dataSource.searchNews(term, this)
    }
    override fun onSuccess(newsResponse: NewsResponse) {
        this.view.showArticles(newsResponse.articles)
    }
    override fun onError(message: String) {
        this.view.showFailure(message)
    }
    override fun onComplete() {
        this.view.hideProgressBar()
    }
}
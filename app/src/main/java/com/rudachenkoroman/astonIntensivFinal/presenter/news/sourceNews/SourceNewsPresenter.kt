package com.rudachenkoroman.astonIntensivFinal.presenter.news.sourceNews

import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome
import com.rudachenkoroman.astonIntensivFinal.presenter.news.NewsHome

class SourceNewsPresenter (
    val view: ViewHome.View,
    private val dataSource: NewsDataSource
) : NewsHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getDetailSourceNews(this)
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
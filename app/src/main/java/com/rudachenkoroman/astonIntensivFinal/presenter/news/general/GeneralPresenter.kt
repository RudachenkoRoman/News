package com.rudachenkoroman.astonIntensivFinal.presenter.news.general

import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.presenter.news.NewsHome
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome

class GeneralPresenter (
    val view: ViewHome.View,
    private val dataSource: NewsDataSource
) : NewsHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getGeneralNews(this)
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
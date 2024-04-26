package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.news.business

import com.rudachenkoroman.astonIntensivFinal.domain.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.news.NewsHome
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.ViewHome

class BusinessPresenter (
    val view: ViewHome.View,
    private val dataSource: NewsDataSource
) : NewsHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getBusinessNews(this)
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
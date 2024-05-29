package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.source

import com.rudachenkoroman.astonIntensivFinal.domain.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.domain.model.source.SourceResponse
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.ViewHome

class SourcePresenter (
    val view: ViewHome.SourceView,
    private val dataSource: NewsDataSource
) : SourceHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getSourceNews(this)
    }
    override fun onSuccess(sourceResponse: SourceResponse) {
        this.view.showSource(sourceResponse.sources)
    }
    override fun onError(message: String) {
        this.view.showFailure(message)
    }
    override fun onComplete() {
        this.view.hideProgressBar()
    }
}
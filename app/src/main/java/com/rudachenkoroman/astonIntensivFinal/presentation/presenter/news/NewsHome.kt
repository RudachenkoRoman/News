package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.news

import com.rudachenkoroman.astonIntensivFinal.domain.model.news.NewsResponse

interface NewsHome {
    interface Presenter {
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}
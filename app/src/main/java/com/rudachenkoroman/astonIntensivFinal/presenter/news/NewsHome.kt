package com.rudachenkoroman.astonIntensivFinal.presenter.news

import com.rudachenkoroman.astonIntensivFinal.model.news.NewsResponse

interface NewsHome {
    interface Presenter {
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}
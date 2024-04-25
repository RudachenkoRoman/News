package com.rudachenkoroman.astonIntensivFinal.presenter.search.newsSearch

import com.rudachenkoroman.astonIntensivFinal.model.news.NewsResponse

interface SearchHome {
    interface Presenter {
        fun search(term: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}
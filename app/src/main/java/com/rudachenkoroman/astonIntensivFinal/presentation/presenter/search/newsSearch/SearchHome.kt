package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.search.newsSearch

import com.rudachenkoroman.astonIntensivFinal.domain.model.news.NewsResponse

interface SearchHome {
    interface Presenter {
        fun search(term: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}
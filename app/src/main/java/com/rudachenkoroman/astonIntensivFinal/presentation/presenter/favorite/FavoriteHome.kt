package com.rudachenkoroman.astonIntensivFinal.presentation.presenter.favorite

import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article


interface FavoriteHome {
    interface Presenter {
        fun onSuccess(articles: List<Article>)
    }
}
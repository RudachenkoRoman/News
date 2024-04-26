package com.rudachenkoroman.astonIntensivFinal.presenter.favorite

import com.rudachenkoroman.astonIntensivFinal.model.news.Article


interface FavoriteHome {
    interface Presenter {
        fun onSuccess(articles: List<Article>)
    }
}
package com.rudachenkoroman.astonIntensivFinal.presenter

import com.rudachenkoroman.astonIntensivFinal.model.news.Article

interface ViewHome {
    interface View{
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(articles: List<Article>)
    }

    interface Favorite{
        fun showArticles(articles: List<Article>)
    }

    interface SourceView{
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showSource(source: List<com.rudachenkoroman.astonIntensivFinal.model.source.Source>)
    }
}
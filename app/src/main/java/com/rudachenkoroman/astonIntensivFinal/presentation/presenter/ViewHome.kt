package com.rudachenkoroman.astonIntensivFinal.presentation.presenter

import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.domain.model.source.Source

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
        fun showSource(source: List<Source>)
    }
}
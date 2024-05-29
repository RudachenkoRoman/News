package com.rudachenkoroman.astonIntensivFinal.domain.model.data

import android.content.Context
import com.rudachenkoroman.astonIntensivFinal.data.api.RetrofitInstance
import com.rudachenkoroman.astonIntensivFinal.domain.model.db.ArticleDatabase
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.favorite.FavoriteHome
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.news.NewsHome
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.search.newsSearch.SearchHome
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.source.SourceHome
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.BusinessFragment
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.DetailSourceFragment
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.GeneralFragment
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.ScienceFragment
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.SourcesFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response

@OptIn(DelicateCoroutinesApi::class)
class NewsDataSource(context: Context) {

    private var db: ArticleDatabase = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)
    fun getGeneralNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = GeneralFragment.requestGeneralNews()
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }
    fun getBusinessNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = BusinessFragment.requestBusinessNews()
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun getScienceNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = ScienceFragment.requestScienceNews()
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun getDetailSourceNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = DetailSourceFragment.requestDetailSourceNews()
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun getSourceNews(callback: SourceHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = SourcesFragment.requestSource()
            if (response.isSuccessful) {
                response.body()?.let { sourceResponse ->
                    callback.onSuccess(sourceResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun searchNews(term: String, callback: SearchHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getSearchNews(term)
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun saveArticle(article: Article) {
        GlobalScope.launch(Dispatchers.Main) {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticle(callback: FavoriteHome.Presenter) {
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll()
            allArticlesSize = allArticles.size
            withContext(Dispatchers.Main) {
                callback.onSuccess(allArticles)
            }
        }
    }

    fun deleteArticle(article: Article?) {
        GlobalScope.launch(Dispatchers.Main) {
            article?.let { articleSafe ->
                newsRepository.delete(articleSafe)
            }
        }
    }

    companion object {
        var allArticlesSize = 0
    }
}
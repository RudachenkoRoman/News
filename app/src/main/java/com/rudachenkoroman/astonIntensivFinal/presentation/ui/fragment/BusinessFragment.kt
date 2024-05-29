package com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.domain.adapter.NewsAdapter
import com.rudachenkoroman.astonIntensivFinal.data.api.RetrofitInstance
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentBusinessBinding
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.domain.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.news.business.BusinessPresenter
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.ViewHome
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.DetailNewsFragment.Companion.DETAIL_NEWS_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.HeadlinesFragment.Companion.language
import com.rudachenkoroman.astonIntensivFinal.presentation.util.setFragment
import retrofit2.Response

class BusinessFragment : Fragment(), ViewHome.View {

    private val newsAdapter = NewsAdapter(onClickNews = { item -> onNewsClick(item) })
    private lateinit var binding: FragmentBusinessBinding
    private lateinit var presenter: BusinessPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBusinessBinding.inflate(layoutInflater)
        val datasource = NewsDataSource(requireContext())
        presenter = BusinessPresenter(this,datasource)
        presenter.requestAll()
        createRecycle()
        return binding.root
    }

    private fun onNewsClick(item: Article) {
        parentFragmentManager.setFragment(
            R.id.fragmentContainerView,
            DetailNewsFragment.newInstance(item),
            DETAIL_NEWS_FRAGMENT_TAG
        )
    }

    private fun createRecycle() {
        with(binding.businessRecyclerView) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        newsAdapter.submitList(articles.toList())
    }

    companion object {
        suspend fun requestBusinessNews(): Response<NewsResponse> {
            return RetrofitInstance.api.getNews(language, BUSINESS)
        }
        const val BUSINESS = "business"
    }
}
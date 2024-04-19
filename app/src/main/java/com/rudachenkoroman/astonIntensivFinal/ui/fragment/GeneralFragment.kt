package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.adapter.NewsAdapter
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentGeneralBinding
import com.rudachenkoroman.astonIntensivFinal.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.presenter.news.general.GeneralPresenter
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome
import com.rudachenkoroman.astonIntensivFinal.util.setFragment

class GeneralFragment : Fragment(), ViewHome.View {

    private val newsAdapter by lazy { NewsAdapter(onClick = { item -> onNewsClick(item) }) }
    private lateinit var binding: FragmentGeneralBinding
    private lateinit var presenter: GeneralPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralBinding.inflate(layoutInflater)

        val datasource = NewsDataSource(requireContext())
        presenter = GeneralPresenter(this,datasource)
        presenter.requestAll()

        createRecycle()

        return binding.root
    }

    private fun createRecycle() {
        with(binding.generalRecyclerView) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun onNewsClick(item:Article) {
        parentFragmentManager.setFragment(
            R.id.fragmentContainerView,
            DetailNewsFragment.newInstance(item),
            DETAIL_NEWS_FRAGMENT_TAG
        )
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(requireActivity(), "Error", Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        newsAdapter.submitList(articles.toList())
    }

}


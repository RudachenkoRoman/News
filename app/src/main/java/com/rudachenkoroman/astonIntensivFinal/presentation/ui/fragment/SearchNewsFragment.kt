package com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.domain.adapter.NewsAdapterSearch
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentSearchNewsBinding
import com.rudachenkoroman.astonIntensivFinal.domain.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.domain.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.search.newsSearch.SearchPresenter
import com.rudachenkoroman.astonIntensivFinal.presentation.util.UtilQueryTextListener
import com.rudachenkoroman.astonIntensivFinal.presentation.presenter.ViewHome
import com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment.DetailNewsFragment.Companion.DETAIL_NEWS_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.presentation.util.setFragment
import com.rudachenkoroman.astonIntensivFinal.presentation.util.setOnBackPressedCallback

class SearchNewsFragment : Fragment() , ViewHome.View {

    private val newsAdapterSearch by lazy { NewsAdapterSearch(onClickSearch = { item -> onClickSearch(item) }) }
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var presenter: SearchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        val datasource = NewsDataSource(requireContext())
        presenter = SearchPresenter(this, datasource)
        toolbarInit()
        backClicked()
        createRecycle()
        search()
        return binding.root
    }

    private fun createRecycle() {
        with(binding.searchRecyclerView) {
            adapter = newsAdapterSearch
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun onClickSearch(item: Article) {
        parentFragmentManager.setFragment(
            R.id.fragmentContainerView,
            DetailNewsFragment.newInstance(item),
            DETAIL_NEWS_FRAGMENT_TAG
        )
    }

    private fun search() {
        val searchView = binding.toolbar.toolbarMain.menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ) { newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()) {
                        presenter.search(query)
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun toolbarInit() {
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_search)
            toolbar.fragmentName.text = getText(R.string.search)
            toolbar.toolbarMain.setBackgroundResource(R.color.primary60)
        }
    }

    private fun backClicked() {
        binding.apply {
            toolbar.toolbarMain.setNavigationIcon(R.drawable.back)
            toolbar.toolbarMain.setNavigationOnClickListener {
                setOnBackPressedCallback()
            }
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
        newsAdapterSearch.submitList(articles.toList())
    }

    companion object{
        const val SEARCH_NEWS_FRAGMENT_TAG = "SEARCH_NEWS_FRAGMENT_TAG"
    }
}
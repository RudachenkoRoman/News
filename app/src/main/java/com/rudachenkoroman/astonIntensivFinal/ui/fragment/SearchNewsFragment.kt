package com.rudachenkoroman.astonIntensivFinal.ui.fragment

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
import com.rudachenkoroman.astonIntensivFinal.adapter.NewsAdapterSearch
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentSearchNewsBinding
import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.presenter.search.SearchPresenter
import com.rudachenkoroman.astonIntensivFinal.util.UtilQueryTextListener
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome

class SearchNewsFragment : Fragment() , ViewHome.View {

    private val newsAdapterSearch by lazy { NewsAdapterSearch() }

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
                requireActivity().onBackPressedDispatcher.onBackPressed()
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
        newsAdapterSearch.differ.submitList(articles.toList())
    }

}
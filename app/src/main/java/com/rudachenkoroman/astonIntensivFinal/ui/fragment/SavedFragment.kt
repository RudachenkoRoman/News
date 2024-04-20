package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.adapter.NewsAdapter
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentSavedBinding
import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome
import com.rudachenkoroman.astonIntensivFinal.presenter.favorite.FavoritePresenter
import com.rudachenkoroman.astonIntensivFinal.util.setFragment

const val SAVED_FRAGMENT_TAG = "SAVED_FRAGMENT_TAG"

class SavedFragment : Fragment(), ViewHome.Favorite{

    private val newsAdapter by lazy { NewsAdapter(onClickNews = { item -> onNewsClick(item) }) }
    private lateinit var binding: FragmentSavedBinding
    private lateinit var presenter: FavoritePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(layoutInflater)

        val datasource = NewsDataSource(requireContext())
        presenter = FavoritePresenter(this, datasource)
        presenter.getAll()
        toolbarInit()
        toolbarMenuItemClick()
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
        with(binding.savedRecyclerView) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun toolbarMenuItemClick(){
        binding.apply {
            toolbar.toolbarMain.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter -> {
                        parentFragmentManager.setFragment(
                            R.id.fragmentContainerView,
                            FiltersFragment(),
                            FILTERS_FRAGMENT_TAG
                        )
                    }
                    R.id.search -> {
                        parentFragmentManager.setFragment(
                            R.id.fragmentContainerView,
                            SearchFragment(),
                            SEARCH_FRAGMENT_TAG
                        )
                    }
                }
                true
            }
        }
    }

    private fun toolbarInit(){
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_main)
            toolbar.fragmentName.text = getText(R.string.saved)
            toolbar.toolbarMain.setBackgroundResource(R.color.primary60)
        }
    }

    override fun showArticles(articles: List<Article>) {
        newsAdapter.submitList(articles.toList())
    }
}
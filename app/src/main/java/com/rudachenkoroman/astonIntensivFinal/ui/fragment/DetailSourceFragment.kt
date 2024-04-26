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
import com.rudachenkoroman.astonIntensivFinal.api.RetrofitInstance
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentDetailSourceBinding
import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.model.source.Source
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome
import com.rudachenkoroman.astonIntensivFinal.presenter.news.sourceNews.SourceNewsPresenter
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.DetailNewsFragment.Companion.DETAIL_NEWS_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.util.getSerializableCompat
import com.rudachenkoroman.astonIntensivFinal.util.setFragment
import retrofit2.Response

class DetailSourceFragment : Fragment(), ViewHome.View {

    private val newsAdapter by lazy { NewsAdapter(onClickNews = { item -> onNewsClick(item) }) }
    private lateinit var binding: FragmentDetailSourceBinding
    private lateinit var presenter: SourceNewsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailSourceBinding.inflate(layoutInflater)
        val datasource = NewsDataSource(requireContext())
        presenter = SourceNewsPresenter(this, datasource)
        presenter.requestAll()
        backClicked()
        createRecycle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sources =
            requireArguments().getSerializableCompat(BUNDLE_KEY_SOURCE, Source::class.java)
        if (sources != null) {
            requestSourceId = sources.id
            toolbarInit(sources)
        }
    }

    private fun toolbarInit(source: Source) {
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_main)
            toolbar.fragmentName.text = source.name
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

    private fun createRecycle() {
        with(binding.detailSourceRecyclerView) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun onNewsClick(item: Article) {
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
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        newsAdapter.submitList(articles.toList())
    }

    companion object {
        fun newInstance(source: Source): DetailSourceFragment {
            return DetailSourceFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_KEY_SOURCE, source)
                }
            }
        }

        suspend fun requestDetailSourceNews(): Response<NewsResponse> {
            return RetrofitInstance.api.getDetailSourceNews(
                SEARCH_QUERY,
                TITLE, requestSourceId
            )
        }

        const val DETAIL_SOURCE_FRAGMENT_TAG = "DETAIL_SOURCE_FRAGMENT_TAG"
        const val BUNDLE_KEY_SOURCE = "BUNDLE_KEY_SOURCE"
        private const val SEARCH_QUERY = "us"
        private const val TITLE = "title"
        private lateinit var requestSourceId: String
    }
}
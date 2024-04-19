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
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentScienceBinding
import com.rudachenkoroman.astonIntensivFinal.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.presenter.news.scince.SciencePresenter
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome

class ScienceFragment : Fragment(), ViewHome.View{

//    private val newsAdapter by lazy { NewsAdapter() }


    private val newsAdapter = NewsAdapter(onClick = { item -> replaceFragment(item) })

    private lateinit var binding: FragmentScienceBinding
    private lateinit var presenter: SciencePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScienceBinding.inflate(layoutInflater)

        val datasource = NewsDataSource(requireContext())
        presenter = SciencePresenter(this,datasource)
        presenter.requestAll()

        with(binding.scienceRecyclerView) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
        return binding.root
    }

    private fun replaceFragment(item:Article) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, DetailNewsFragment())
            .addToBackStack(tag)
            .setReorderingAllowed(true)
            .commit()
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

}
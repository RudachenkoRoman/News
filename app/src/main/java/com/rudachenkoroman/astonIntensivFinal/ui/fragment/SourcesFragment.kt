package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.adapter.SourceAdapter
import com.rudachenkoroman.astonIntensivFinal.api.NewsApi
import com.rudachenkoroman.astonIntensivFinal.api.RetrofitInstance
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentSourcesBinding
import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.model.source.Source
import com.rudachenkoroman.astonIntensivFinal.model.source.SourceResponse
import com.rudachenkoroman.astonIntensivFinal.presenter.source.SourcePresenter
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.DetailSourceFragment.Companion.DETAIL_SOURCE_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.FiltersFragment.Companion.FILTERS_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.util.UtilQueryTextListener
import com.rudachenkoroman.astonIntensivFinal.util.finishFragment
import com.rudachenkoroman.astonIntensivFinal.util.setFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SourcesFragment : Fragment() , ViewHome.SourceView {

    private val sourceAdapter by lazy { SourceAdapter(onClickSource = { item -> onSourceClick(item) }) }
    private lateinit var binding: FragmentSourcesBinding
    private lateinit var presenter: SourcePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSourcesBinding.inflate(layoutInflater)
        val datasource = NewsDataSource(requireContext())
        presenter = SourcePresenter(this,datasource)
        presenter.requestAll()
        toolbarInit()
        toolbarMenuItemClick()
        createRecycle()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        finishFragment()
    }

    private fun createRecycle() {
        with(binding.sourceRecyclerView) {
            adapter = sourceAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun onSourceClick(item: Source){
        parentFragmentManager.setFragment(
            R.id.fragmentContainerView,
            DetailSourceFragment.newInstance(item),
            DETAIL_SOURCE_FRAGMENT_TAG
        )
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
                        searchSource()
                    }
                }
                true
            }
        }
    }

    private fun searchSource() {
        val searchView = binding.toolbar.toolbarMain.menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun toolbarInit(){
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_search)
            toolbar.fragmentName.text = getText(R.string.sources)
            toolbar.toolbarMain.setBackgroundResource(R.color.primary60)
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

    override fun showSource(source: List<Source>) {
        sourceAdapter.submitList(source.toList())
    }

    companion object {
        suspend fun requestSource(): Response<SourceResponse> {
            return RetrofitInstance.api.getSource(COUNTY_CODE_US)
        }
        private const val COUNTY_CODE_US = "us"
        const val SOURCE_FRAGMENT_TAG = "SOURCE_FRAGMENT_TAG"
    }
}
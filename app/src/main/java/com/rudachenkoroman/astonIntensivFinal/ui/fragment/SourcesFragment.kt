package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.adapter.SourceAdapter
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentSourcesBinding
import com.rudachenkoroman.astonIntensivFinal.model.data.NewsDataSource
import com.rudachenkoroman.astonIntensivFinal.model.source.Source
import com.rudachenkoroman.astonIntensivFinal.presenter.source.SourcePresenter
import com.rudachenkoroman.astonIntensivFinal.presenter.ViewHome

const val SOURCE_FRAGMENT_TAG = "SOURCE_FRAGMENT_TAG"

class SourcesFragment : Fragment() , ViewHome.SourceView {

    private val sourceAdapter by lazy { SourceAdapter() }

    private lateinit var binding: FragmentSourcesBinding
    private lateinit var presenter: SourcePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSourcesBinding.inflate(layoutInflater)
        binding.apply {
            toolbarInit()
            toolbarMenuItemClick()
        }

        val datasource = NewsDataSource(requireContext())
        presenter = SourcePresenter(this,datasource)
        presenter.requestAll()

        with(binding.sourceRecyclerView) {
            adapter = sourceAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
        return binding.root
    }

    private fun toolbarMenuItemClick(){
        binding.apply {
            toolbar.toolbarMain.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter -> replaceFragment(FiltersFragment())
                    R.id.search -> replaceFragment(SearchFragment())
                }
                true
            }
        }
    }

    private fun toolbarInit(){
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_main)
            toolbar.fragmentName.text = getText(R.string.sources)
            toolbar.toolbarMain.setBackgroundResource(R.color.primary60)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
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

    override fun showSource(source: List<Source>) {
        sourceAdapter.differ.submitList(source.toList())
    }
}
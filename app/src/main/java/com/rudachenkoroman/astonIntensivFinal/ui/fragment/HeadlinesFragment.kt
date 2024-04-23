package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.adapter.FragmentPageAdapter
import com.rudachenkoroman.astonIntensivFinal.api.NewsApi
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentHeadlinesBinding
import com.rudachenkoroman.astonIntensivFinal.util.setFragment

const val HEADLINES_FRAGMENT_TAG = "HEADLINES_FRAGMENT_TAG"

class HeadlinesFragment : Fragment() {
    private lateinit var binding: FragmentHeadlinesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeadlinesBinding.inflate(layoutInflater)
        binding.apply {
            toolbarInit()
            toolbarMenuItemClick()
            initPageAdapter()
            tabLayoutMenuClick()
            viewPagerCallback()
        }
        return binding.root
    }

    private fun viewPagerCallback() {
        binding.apply {
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }
    }

    private fun tabLayoutMenuClick() {
        binding.apply {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewPager2.currentItem = tab.position
                    }
                    when(viewPager2.currentItem){
                        0-> NewsApi.requestCategorySearch(GeneralFragment.GENERAL)
                        1-> NewsApi.requestCategorySearch(BusinessFragment.BUSINESS)
                        2-> NewsApi.requestCategorySearch(ScienceFragment.SCIENCE)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }
    }

    private fun initPageAdapter() {
        binding.apply {
            val adapter = FragmentPageAdapter(requireActivity())
            viewPager2.adapter = adapter
            adapter.addFragment(GeneralFragment())
            adapter.addFragment(BusinessFragment())
            adapter.addFragment(ScienceFragment())
        }
    }

    private fun toolbarMenuItemClick() {
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

    private fun toolbarInit() {
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_main)
            toolbar.fragmentName.text = getText(R.string.newsApp)
            toolbar.toolbarMain.setBackgroundResource(R.color.primary60)
        }
    }

}
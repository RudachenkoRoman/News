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
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentHeadlinesBinding

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

//            val searchMenuItem = toolbar.toolbarMain.menu.findItem(R.id.search)
//            searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
//                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
//                    toolbar.toolbarMain.menu.findItem(R.id.filter).isVisible = false
//                    tabLayout.isVisible = false
//                    return true
//                }
//
//                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
//                    toolbar.toolbarMain.menu.findItem(R.id.filter).isVisible = true
//                    tabLayout.isVisible = true
//                    return true
//                }
//            })
//
//            val searchView = toolbar.toolbarMain.menu.findItem(R.id.search).actionView as SearchView
//            searchView.setOnQueryTextListener(object : OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return true
//                }
//
//            })
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
                        replaceFragment(FiltersFragment())
                    }
                    R.id.search -> {
                        replaceFragment(SearchNewsFragment())
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

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(fragment.tag)
            .setReorderingAllowed(true)
            .commit()
    }
}
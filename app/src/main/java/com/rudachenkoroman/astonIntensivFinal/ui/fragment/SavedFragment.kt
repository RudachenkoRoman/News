package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentSavedBinding

class SavedFragment : Fragment(){

    private lateinit var binding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(layoutInflater)
        binding.apply {
            toolbarInit()
            toolbarMenuItemClick()
        }
        return binding.root
    }

    private fun toolbarMenuItemClick(){
        binding.apply {
            toolbar.toolbarMain.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter -> replaceFragment(FiltersFragment())
                    R.id.search -> {
                        toolbar.toolbarMain.menu.findItem(R.id.filter).isVisible = false
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

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }

}
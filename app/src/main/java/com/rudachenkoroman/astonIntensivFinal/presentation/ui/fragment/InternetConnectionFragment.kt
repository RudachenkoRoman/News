package com.rudachenkoroman.astonIntensivFinal.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentInternetConnectionBinding
import com.rudachenkoroman.astonIntensivFinal.data.network.NetworkManager
import com.rudachenkoroman.astonIntensivFinal.presentation.util.setFragment

class InternetConnectionFragment : Fragment() {
    private lateinit var binding: FragmentInternetConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInternetConnectionBinding.inflate(layoutInflater)
        onClickRefresh()
        toolbarInit()
        return binding.root
    }

    private fun onClickRefresh() {
        binding.apply {
            refreshConnection.setOnClickListener {
                animRefresh()
                checkConnectionInternet()
            }
        }
    }

    private fun checkConnectionInternet() {
        val networkManager = NetworkManager(requireActivity())
        if (!networkManager.isNetworkRefresh()) {
            snackbarRefreshConnection()
        } else {
            parentFragmentManager.setFragment(
                R.id.fragmentContainerView,
                HeadlinesFragment(),
                HeadlinesFragment.HEADLINES_FRAGMENT_TAG
            )
        }
    }

    private fun snackbarRefreshConnection() {
        view?.let { it1 ->
            Snackbar.make(
                it1, R.string.refreshConnection,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun animRefresh() {
        binding.apply {
            val rotate = AnimationUtils.loadAnimation(requireActivity(), R.anim.rotate_animation)
            refreshConnection.startAnimation(rotate)
        }
    }

    private fun toolbarInit() {
        binding.apply {
            toolbar.toolbarMain.setBackgroundResource(R.color.primary60)
        }
    }

    companion object {
        const val INTERNET_CONNECTION_FRAGMENT_TAG = "INTERNET_CONNECTION_FRAGMENT_TAG"
    }
}
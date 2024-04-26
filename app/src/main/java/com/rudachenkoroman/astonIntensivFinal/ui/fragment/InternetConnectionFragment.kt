package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentInternetConnectionBinding
import com.rudachenkoroman.astonIntensivFinal.network.NetworkManager

class InternetConnectionFragment : Fragment() {
    private lateinit var binding: FragmentInternetConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInternetConnectionBinding.inflate(layoutInflater)
        onClickRefresh()
        return binding.root
    }

    private fun onClickRefresh() {
        binding.apply {
            refreshConnection.setOnClickListener {
                animRefresh()
                checkConnection()
            }
        }
    }

    private fun checkConnection() {
        val networkManager = NetworkManager(requireActivity())
        networkManager.observe(requireActivity()) {
            if (!it) {
                snackbarRefreshConnection()
            }
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

    companion object {
        const val INTERNET_CONNECTION_FRAGMENT_TAG = "INTERNET_CONNECTION_FRAGMENT_TAG"
    }
}
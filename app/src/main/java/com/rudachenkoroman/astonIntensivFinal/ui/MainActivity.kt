package com.rudachenkoroman.astonIntensivFinal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.ActivityMainBinding
import com.rudachenkoroman.astonIntensivFinal.network.NetworkManager
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.HeadlinesFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.HeadlinesFragment.Companion.HEADLINES_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.InternetConnectionFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.InternetConnectionFragment.Companion.INTERNET_CONNECTION_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SavedFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SavedFragment.Companion.SAVED_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SourcesFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SourcesFragment.Companion.SOURCE_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.util.setFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var keepSplashOnScreen = true
    private val delay = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkConnection()
        buttonSelectionBottomNavigation()
    }

    private fun initSplashScreen() {
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
    }

    private fun buttonSelectionBottomNavigation() {
        binding.apply {
            bottomNavigation.bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.headlines -> {
                        supportFragmentManager.setFragment(
                            R.id.fragmentContainerView,
                            HeadlinesFragment(),
                            HEADLINES_FRAGMENT_TAG
                        )

                    }

                    R.id.saved -> {
                        supportFragmentManager.setFragment(
                            R.id.fragmentContainerView,
                            SavedFragment(),
                            SAVED_FRAGMENT_TAG
                        )
                    }

                    R.id.sources -> {
                        supportFragmentManager.setFragment(
                            R.id.fragmentContainerView,
                            SourcesFragment(),
                            SOURCE_FRAGMENT_TAG
                        )
                    }
                }
                true
            }
        }
    }

    private fun checkConnection(){
        val networkManager = NetworkManager(this)
        networkManager.observe(this) {
            if (!it) {
                bottomNavigationNotConnection()
                supportFragmentManager.setFragment(
                    R.id.fragmentContainerView,
                    InternetConnectionFragment(),
                    INTERNET_CONNECTION_FRAGMENT_TAG
                )
            } else {
                bottomNavigationConnection()
                supportFragmentManager.setFragment(
                    R.id.fragmentContainerView,
                    HeadlinesFragment(),
                    HEADLINES_FRAGMENT_TAG
                )
            }
        }
    }

    private fun bottomNavigationNotConnection(){
        binding.apply {
            binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.saved
            binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.headlines).isEnabled = false
            binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.sources).isEnabled = false
        }
    }

    private fun bottomNavigationConnection(){
        binding.apply {
            binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.headlines
            binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.headlines).isEnabled = true
            binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.sources).isEnabled = true
        }
    }
}
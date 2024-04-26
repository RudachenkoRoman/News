package com.rudachenkoroman.astonIntensivFinal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.api.NewsApi
import com.rudachenkoroman.astonIntensivFinal.databinding.ActivityMainBinding
import com.rudachenkoroman.astonIntensivFinal.network.NetworkManager
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.HEADLINES_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.HeadlinesFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.InternetConnectionFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.InternetConnectionFragment.Companion.INTERNET_CONNECTION_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SAVED_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SOURCE_FRAGMENT_TAG
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SavedFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SourcesFragment
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

        val networkManager = NetworkManager(this)
        networkManager.observe(this) {
            if (!it) {
                binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.saved
                binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.headlines).isEnabled = false
                binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.sources).isEnabled = false
                supportFragmentManager.setFragment(
                    R.id.fragmentContainerView,
                    InternetConnectionFragment(),
                    INTERNET_CONNECTION_FRAGMENT_TAG
                )
            } else {
                binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.headlines
                binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.headlines).isEnabled = true
                binding.bottomNavigation.bottomNavigation.menu.findItem(R.id.sources).isEnabled = true
                supportFragmentManager.setFragment(
                    R.id.fragmentContainerView,
                    HeadlinesFragment(),
                    HEADLINES_FRAGMENT_TAG
                )
            }
        }

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
}
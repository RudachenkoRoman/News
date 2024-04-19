package com.rudachenkoroman.astonIntensivFinal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.ActivityMainBinding
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.HeadlinesFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SavedFragment
import com.rudachenkoroman.astonIntensivFinal.ui.fragment.SourcesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var keepSplashOnScreen = true
    private val delay = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            replaceFragmentBottomNavigation(HeadlinesFragment())
        }
        buttonSelectionBottomNavigation()
    }

    private fun initSplashScreen(){
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
    }

    private fun buttonSelectionBottomNavigation() {
        binding.apply {
            bottomNavigation.bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.headlines -> {
                        replaceFragmentBottomNavigation(HeadlinesFragment())
                    }

                    R.id.saved -> {
                        replaceFragmentBottomNavigation(SavedFragment())
                    }

                    R.id.sources -> {
                        replaceFragmentBottomNavigation(SourcesFragment())
                    }
                }
                true
            }
        }
    }

    private fun replaceFragmentBottomNavigation(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .setReorderingAllowed(true)
            .commit()
    }
}
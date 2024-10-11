package com.example.p57_news.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.p57_news.R
import com.example.p57_news.databinding.ActivityMainBinding
import com.example.p57_news.db.MainDatabase
import com.example.p57_news.repository.Repository
import com.example.p57_news.ui.fragment.FavoritesFragment
import com.example.p57_news.ui.fragment.HomeFragment
import com.example.p57_news.ui.fragment.SearchFragment
import com.example.p57_news.viewmodel.MainViewModel
import com.example.p57_news.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1200)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(HomeFragment())

        initialiseViewModel()
        initialiseBottomNavMenu()
    }

    private fun initialiseViewModel() {
        val db = MainDatabase.getDatabase(this)
        val repository = Repository(db)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, fragment)
            .commit()
    }

    private fun initialiseBottomNavMenu() {
        binding.bottomNavigationMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    setFragment(HomeFragment())
                    true
                }

                R.id.menu_favorites -> {
                    setFragment(FavoritesFragment())
                    true
                }

                R.id.menu_search -> {
                    setFragment(SearchFragment())
                    true
                }

                else -> false
            }
        }
    }

}
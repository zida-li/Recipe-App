package us.zidali.recipeapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import us.zidali.recipeapp.R
import us.zidali.recipeapp.databinding.ActivityMainBinding
import us.zidali.recipeapp.presentation.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragments_container) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBar()
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemReselectedListener {item->

            when(item.itemId) {
                R.id.nav_search -> {
                    navController.navigateUp()
                }
                R.id.nav_favorite -> {
                    navController.popBackStack(R.id.favoriteFragment, inclusive = false)
                }
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolBar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.searchFragment,
                R.id.favoriteFragment,
                R.id.shoppingListFragment,
                R.id.nav_apikey
            )
        )
        binding.toolBar.setupWithNavController(navController, appBarConfiguration)
    }


    override fun displayProgressBar(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun expandAppBar() {

    }

    override fun hideAppBar() {

    }

}
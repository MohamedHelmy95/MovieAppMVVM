package com.example.mymvdb.ui.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymvdb.R
import com.example.mymvdb.database.FavoriteMovieDatabase
import com.example.mymvdb.ui.ui.favorites.FavoritesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
private lateinit var favoriteMovieDatabase: FavoriteMovieDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_toprated, R.id.nav_popular, R.id.nav_upcoming,R.id.nav_favorites))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        favoriteMovieDatabase= FavoriteMovieDatabase.getInstance(this)

        lifecycleScope.launch(Dispatchers.IO) { FavoritesFragment.Movies.movies =favoriteMovieDatabase.favoritesDatabaseDAO.getAll()

        }
    }
}
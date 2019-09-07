package com.mcgregor.burns.stadiumfinder

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfig = AppBarConfiguration(navGraph = navController.graph)
        //appBarConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfig)

        bottom_nav.setupWithNavController(navController)
        bottom_nav.menu.findItem(R.id.homeMenuItem).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {item ->
                navController.navigate(R.id.leagueFragment)
                true
            })
        }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()

}

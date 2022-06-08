package com.example.progettoprogrammazionemobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //val navController = this.findNavController(R.id.myNavHostFragment)
        setSupportActionBar(findViewById(R.id.topAppBar))


        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.icon_user -> {
                        val navController = this.findNavController(R.id.myNavHostFragment)
                        navController.navigate(R.id.action_homeFragment_to_profilo)
                        return@OnNavigationItemSelectedListener true
                    }

                }
                false
            }

        val bottomAppBar = findViewById<BottomNavigationView>(R.id.bottomAppBar)
        bottomAppBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bottom_menu, menu)
        return true
    }
}

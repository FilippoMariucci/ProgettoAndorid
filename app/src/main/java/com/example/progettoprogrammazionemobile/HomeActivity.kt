package com.example.progettoprogrammazionemobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private val homeFragment = com.example.progettoprogrammazionemobile.homeFragment()
    private val userFragment = profilo()
    private val creaOccasioneFragment = crea_occasione()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        //val navController = this.findNavController(R.id.myNavHostFragment)
        //setSupportActionBar(findViewById(R.id.topAppBar))
        replaceFragment(homeFragment)

        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.icon_user -> {
                        replaceFragment(userFragment)
                        //val navController = this.findNavController(R.id.myNavHostFragment)
                        //navController.navigate(R.id.action_homeFragment_to_profilo)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.icon_discover -> {
                        replaceFragment(homeFragment)
                        //val navController = this.findNavController(R.id.myNavHostFragment)
                        //navController.navigate(R.id.action_homeFragment_to_profilo)
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.icon_add -> {
                        replaceFragment(creaOccasioneFragment)
                        //val navController = this.findNavController(R.id.myNavHostFragment)
                        //navController.navigate(R.id.action_homeFragment_to_profilo)
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

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.myNavHostFragment, fragment)
            transaction.commit()
        }
    }
}

package com.example.progettoprogrammazionemobile

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationRequestCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.channels.ActorScope
import java.net.URI.create
import java.util.jar.Manifest

class HomeActivity : AppCompatActivity() {


    private val homeFragment = com.example.progettoprogrammazionemobile.homeFragment()
    private val userFragment = com.example.progettoprogrammazionemobile.profilo()
    private val creaOccasioneFragment = crea_occasione()
    private  val mappa = MapsFragment()

    private lateinit var currrentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, homeFragment()).commit()
        val bottomNav : BottomNavigationView = findViewById(R.id.bottomAppBar)
        bottomNav.setOnNavigationItemSelectedListener (navListener)

    }

    val navListener = BottomNavigationView.OnNavigationItemSelectedListener{
        when(it.itemId){
            R.id.icon_user -> {
                currrentFragment = userFragment
            }
            R.id.icon_add -> {
                currrentFragment = creaOccasioneFragment
            }
            R.id.icon_discover -> {
                currrentFragment = homeFragment
            }
            R.id.icon_map -> {
                currrentFragment = mappa
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, currrentFragment).commit()
        true
    }

}




























        /*
        //supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, homeFragment()).commit()

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
//        val navController = navHostFragment.navController
        //val bottomNav : BottomNavigationView = findViewById(R.id.bottomAppBar)
        //val navController = this.findNavController(R.id.myNavHostFragment)
        //setSupportActionBar(findViewById(R.id.topAppBar))

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

         */

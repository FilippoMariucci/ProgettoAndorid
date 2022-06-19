package com.example.progettoprogrammazionemobile

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.example.progettoprogrammazionemobile.ViewModel.imageViewModel

//import androidx.paging.Pager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        setContentView(R.layout.activity_main)

        val updateHandler = Handler()

        val runnable = Runnable {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        updateHandler.postDelayed(runnable, 2000)

    }



}
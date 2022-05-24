package com.example.progettoprogrammazionemobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.progettoprogrammazionemobile.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://programmazionemobile-a1b11-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //remeber that we are gonna initializa biding before settinf the content view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationRoot.setOnClickListener(){
            startActivity(Intent(this, Registration::class.java))
        }

    }


}
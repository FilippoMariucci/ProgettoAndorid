package com.example.progettoprogrammazionemobile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.progettoprogrammazionemobile.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Tag
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private var database = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registrationButton.setOnClickListener{ registrationFunction() }

    }

    private fun registrationFunction() {
        val textName = binding.nome.text.toString().trim()
        val textSurname = binding.surname.text.toString().trim()
        val textEmail = binding.email.text.toString()
        val textState = binding.state.text.toString().trim()
        val textPassword = binding.password.text.toString()
        val textConPassword = binding.passconfirm.text.toString().trim()
        val textdateOfBirth = binding.dateofbirth.text.toString().trim()
        val description = binding.description.text.toString().trim()
        val check = checkFields(textName, textSurname, textEmail, textPassword, textConPassword, textdateOfBirth)
        auth = Firebase.auth



        if (check == true) {
            val user = User(textName, textSurname, textConPassword, textdateOfBirth, textState, description)
            auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "You've been succesfully registred!", Toast.LENGTH_LONG).show()
                    val firebaseUser: FirebaseUser = it.result!!.user!!
                    firebaseUser.
                }
                else{
                    Toast.makeText(this, "sorry", Toast.LENGTH_LONG).show()

                }
            }
        }

   }

    private fun checkFields(textName: String, textSurname: String, textEmail: String, textPassword: String, textConPassword: String, textdateOfBirth: String): Boolean {
        if (textEmail.isEmpty()) {
            binding.email.setError("email is required")
            binding.email.requestFocus()
            return false
        }

        if (textName.isEmpty()) {
            binding.nome.setError("Name is required")
            binding.nome.requestFocus()
            return false
        }
        if (textSurname.isEmpty()) {
            binding.surname.setError("Surname is required")
            binding.surname.requestFocus()
            return false

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
            binding.email.setError("Email missing @!")
            binding.email.requestFocus()
            return false

        }

        if (textPassword.isEmpty()) {
            binding.password.setError("obv Password is required")
            binding.nome.requestFocus()
            return false
        }

        if(textPassword.length<6){
            binding.password.setError("obv Password MUST BE AT LEAST 6 CHARACTERS")
            binding.nome.requestFocus()
        }

        if (textConPassword.isEmpty()) {
            binding.passconfirm.setError("Confirm your password please")
            binding.passconfirm.requestFocus()
            return false

        }
        if (textdateOfBirth.isEmpty()) {
            binding.dateofbirth.setError("Your birth is required")
            binding.dateofbirth.requestFocus()
            return false

        }

        if(!textPassword.equals(textPassword)){
            binding.passconfirm.setError("Passwords don't match!")
            binding.passconfirm.setText(" ")
            return false
        }
        else
            return true
    }
}
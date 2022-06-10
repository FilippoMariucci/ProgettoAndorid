package com.example.progettoprogrammazionemobile.ViewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoprogrammazionemobile.crea_occasione
import com.example.progettoprogrammazionemobile.homeFragment
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.database.*

class EventoViewModel: ViewModel() {

        private lateinit var reference: DatabaseReference
        private val events: MutableList<Evento> = mutableListOf()



        fun saveEvent(event_to_save: Evento) {
            var ritorno = false
            reference = FirebaseDatabase.getInstance().getReference("Evento")
            val id_evento = reference.push().getKey();
            if (id_evento != null) {
            reference.child(id_evento).setValue(event_to_save)
                .addOnCompleteListener{
                        ritorno = true
                }.addOnFailureListener{
                        ritorno = false
                }
            }
            print(ritorno)
        }
}
package com.example.progettoprogrammazionemobile.ViewModel

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoprogrammazionemobile.crea_occasione
import com.example.progettoprogrammazionemobile.homeFragment
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class EventoViewModel: ViewModel() {

        private lateinit var reference: DatabaseReference
        private lateinit var storeRef : StorageReference
        private lateinit var imageUri: Uri



        fun saveEvent(event_to_save: Evento) {
            var ritorno = false
            reference = FirebaseDatabase.getInstance().getReference("Evento")
            event_to_save.id_evento = reference.push().getKey();
            if (event_to_save.id_evento != null) {
            reference.child(event_to_save.id_evento!!).setValue(event_to_save)
                .addOnCompleteListener{
                        if (it.isSuccessful) {
                            ritorno = true
                        }
                }.addOnFailureListener{
                        ritorno = false
                }
            }
            print(ritorno)
        }
}
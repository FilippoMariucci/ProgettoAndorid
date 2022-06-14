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
import java.util.*
import kotlin.collections.ArrayList

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

        fun getDateTimeCalendar(): ArrayList<Int> {
            val cal = Calendar.getInstance()
            var array = arrayListOf<Int>()
            var day = cal.get(Calendar.DAY_OF_MONTH)
            var month = cal.get(Calendar.MONTH)
            var year = cal.get(Calendar.YEAR)
            var hour = cal.get(Calendar.HOUR)
            var minute = cal.get(Calendar.MINUTE)
            array.add(day)
            array.add(month)
            array.add(year)
            array.add(hour)
            array.add(minute)

            return array

        }
}
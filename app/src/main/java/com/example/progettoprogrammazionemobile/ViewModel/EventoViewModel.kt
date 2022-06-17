package com.example.progettoprogrammazionemobile.ViewModel

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.AdapterRV.AdapterImageEvent
import com.example.progettoprogrammazionemobile.crea_occasione
import com.example.progettoprogrammazionemobile.databinding.FragmentCreaOccasioneBinding
import com.example.progettoprogrammazionemobile.homeFragment
import com.example.progettoprogrammazionemobile.model.Evento
import com.example.progettoprogrammazionemobile.occasioni_create
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class EventoViewModel: ViewModel() {

        private lateinit var reference: DatabaseReference
        private lateinit var storeRef : StorageReference
        private lateinit var imageUri: Uri
        private lateinit var listPartecipanti : ArrayList<String>
        lateinit var creaOccasione : crea_occasione
        private lateinit var  databaseReferenceEvento: DatabaseReference
        private lateinit var  storageReference: StorageReference
        private var ritorno by Delegates.notNull<Boolean>()
        private lateinit var auth: FirebaseAuth


        fun saveEvent(event_to_save: Evento) : Boolean {
            reference = FirebaseDatabase.getInstance().getReference("Evento")
            event_to_save.id_evento = reference.push().getKey();
            uploadEventPicture(event_to_save.id_evento)

            if (event_to_save.id_evento != null) {
                ritorno = true
                reference.child(event_to_save.id_evento!!).setValue(event_to_save)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            ritorno = true
                        }
                    }.addOnFailureListener {
                        ritorno = false
                    }
            }
            return  ritorno
        }

    public fun setUri(imageUri: Uri){

        this.imageUri = imageUri
    }


    public fun uploadEventPicture (idEvento: String ?= null) {


        auth = FirebaseAuth.getInstance()

        storageReference = FirebaseStorage.getInstance().getReference("Users/" + idEvento)
        storageReference.putFile(imageUri)

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
    /*
    private fun updateEvent( titolo : String, descrizione : String, citta : String, categoria : String,
                                 indirizzo : String, nPersone : String, costo : String, lingua : String,
                                 data : String,) {
            val event = mapOf<String, String >(
                "titolo" to titolo,
                "categoria" to categoria,
                "citta" to citta,
                "costo" to costo,
                "data_evento" to data,
                "descrizione" to descrizione,
                "indirizzo" to indirizzo,
                "lingue" to lingua,
                "n_persone" to nPersone
            )
            databaseRef.updateChildren(event).addOnSuccessListener {
                Toast.makeText(requireContext(), " Evento Modificato con successo", Toast.LENGTH_SHORT).show()
                fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, occasioni_create())?.commit()
            }
        }

    }

     */
}
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

class EventoViewModel: ViewModel() {

    private lateinit var reference: DatabaseReference
    private lateinit var storeRef : StorageReference
    private lateinit var imageUri: Uri
    private lateinit var listPartecipanti : ArrayList<String>
    lateinit var creaOccasione : crea_occasione
    private lateinit var  databaseReferenceEvento: DatabaseReference
    private lateinit var  storageReference: StorageReference
    private lateinit var auth: FirebaseAuth



    fun saveEvent(event_to_save: Evento) {
            var ritorno = false
            reference = FirebaseDatabase.getInstance().getReference("Evento")
            event_to_save.id_evento = reference.push().getKey();

            val url_storage = "gs://programmazionemobile-a1b11.appspot.com/Users/ + ${event_to_save.id_evento}"
            event_to_save.foto = url_storage

            uploadEventPicture(event_to_save.id_evento)

            if (event_to_save.id_evento != null) {
                reference.child(event_to_save.id_evento!!).setValue(event_to_save)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            ritorno = true
                        }
                    }.addOnFailureListener {
                        ritorno = false
                    }
            }
            print(ritorno)
        }

    fun setUri(imageUri: Uri){
        this.imageUri = imageUri
    }


    // upload su storage
    fun uploadEventPicture (idEvento: String ?= null) {
        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference("Users/" + idEvento)
        storageReference.putFile(imageUri)
    }



}
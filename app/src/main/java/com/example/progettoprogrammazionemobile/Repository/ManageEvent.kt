package com.example.progettoprogrammazionemobile.Repository

import android.net.Uri
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ManageEvent {

    private lateinit var imageUri: Uri
    var  auth = FirebaseAuth.getInstance()
    var storageReference = FirebaseStorage.getInstance()
    var reference = FirebaseDatabase.getInstance().getReference("Evento")

    fun saveNewEvent (event_to_save : Evento) {
            event_to_save.id_evento = reference.push().getKey();
            uploadEventPicture(event_to_save.id_evento)
            if (event_to_save.id_evento != null) {
//                reference.child(event_to_save.id_evento!!).setValue(event_to_save)
//                    .addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            ritorno = true
//                        }
//                    }.addOnFailureListener {
//                        ritorno = false
//                    }
//            }
//            return  ritorno
        }

    }
    fun setUri(imageUri: Uri){
        this.imageUri = imageUri
    }


    fun uploadEventPicture (idEvento: String ?= null) {
        auth = FirebaseAuth.getInstance()
        storageReference.getReference("Users/" + idEvento)
        //storageReference.putFile(imageUri)
    }
}
package com.example.progettoprogrammazionemobile.FirebaseDatabase

import android.util.Log
import com.example.appericolo.ui.preferiti.contacts.database.EventoDb
import com.example.appericolo.ui.preferiti.contacts.database.EventsRoomDb
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsDataFirebase(private val database: EventsRoomDb) {
    var eventList = ArrayList<EventoDb>()

    var databaseRemote: DatabaseReference = FirebaseDatabase.getInstance(
        "https://programmazionemobile-a1b11-default-rtdb.firebaseio.com/")
        .getReference("Evento")

    lateinit var dbRef : DatabaseReference

    fun getList(): ArrayList<EventoDb> {
        Log.d("getlist", "${this.eventList}")
        return this.eventList
    }

    fun boh(): Boolean {
        getEvents()
        return true
    }

    fun getAllEvents()  = CoroutineScope(Dispatchers.IO).launch{
        dbRef = FirebaseDatabase.getInstance().getReference("Evento")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eventSnap in snapshot.children) {
                        val eventoSingolo = eventSnap.getValue(EventoDb::class.java)
                        if (eventoSingolo != null) {
                            database.eventoDao().insert(eventoSingolo)
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



    fun getEvents() {
        databaseRemote.get().addOnSuccessListener {
            events ->
            val dio = ArrayList<EventoDb>()
            for(evento in events.children){
                val eventoSingolo = evento.getValue(EventoDb::class.java)
//                if (eventoSingolo != null) {
//                    database.eventoDao().insert(eventoSingolo)
//                }
            Log.d("fica1", "$eventoSingolo")
                dio.add(eventoSingolo!!)
            Log.d("diocane", "$dio")
            }
            setList(dio)
        }.addOnFailureListener{
            Log.d("erroreFirebase", "errore")
        }
        Log.d("fica", "${eventList}")
        Thread.sleep(3000)
    }

    private fun setList(eventdio: ArrayList<EventoDb>){
        this.eventList = eventdio
        Log.d("hovinto", "${eventList}")
    }


}
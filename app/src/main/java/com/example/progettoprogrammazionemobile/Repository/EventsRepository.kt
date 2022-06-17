package com.example.progettoprogrammazionemobile.Repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.appericolo.ui.preferiti.contacts.database.EventoDb
import com.example.appericolo.ui.preferiti.contacts.database.EventsRoomDb
import com.example.progettoprogrammazionemobile.FirebaseDatabase.EventsDataFirebase

class EventsRepository(private val database: EventsRoomDb) {

    // to avoid mismatch problem
    var eventsData = EventsDataFirebase(database)
    var events: LiveData<List<EventoDb>> = database.eventoDao().getAllEvents()
    //var filtered: LiveData<List<EventoDb>> = database.eventoDao().filterCategory("Adventure")

    fun getDataFromRemote() {
       database.clearAllTables()
//        eventsData.getAllEvents()
        var list = eventsData.boh()
        var prova = ArrayList<EventoDb>()
        if(list) {prova = eventsData.getList()}
        for (evento in prova){
            Log.d("giacomo", "$evento")
            database.eventoDao().insert(evento)
        }
    }

    fun filterCat(titleCat: String) : List<EventoDb>{
        val filtered = database.eventoDao().filterCategory(titleCat)
        return filtered
    }

    fun insert(model: EventoDb, imageUri: Uri) {
        database.eventoDao().insert(model)
        eventsData.inserEventRemote(model, imageUri)
    }
}

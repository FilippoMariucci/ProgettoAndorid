package com.example.appericolo.ui.preferiti.contacts.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.progettoprogrammazionemobile.model.Evento

@Dao
interface EventoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact : EventoDb)

    @Delete
    fun delete(contact: EventoDb)

    @Query("SELECT * FROM evento_table")
    fun getAllEvents(): LiveData<List<EventoDb>>

    @Query("DELETE FROM evento_table")
    fun deleteAll()

    @Query("UPDATE evento_table SET foto = :url WHERE id_evento = :id_evento")
    fun update_foto(url: String, id_evento: String)

    @Query("SELECT * FROM evento_table WHERE id_evento = :id_evento")
    fun getEventoFromId(id_evento: String): EventoDb

    @Query("SELECT * FROM evento_table WHERE categoria = :titleCat")
    fun filterCategory(titleCat: String): List<EventoDb>

//    @Query("SELECT * FROM evento_table")
//    fun getUserEvent(id_user: String): LiveData<List<EventoDb>>

}
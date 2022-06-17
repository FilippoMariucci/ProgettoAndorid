package com.example.progettoprogrammazionemobile.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.appericolo.ui.preferiti.contacts.database.EventoDb
import com.example.appericolo.ui.preferiti.contacts.database.EventsRoomDb
import com.example.progettoprogrammazionemobile.Repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class eventViewModel(application: Application) : AndroidViewModel(application) {

    var readEventData: LiveData<List<EventoDb>>
    private val eventsRepository: EventsRepository

    val filterEventsLiveData = MutableLiveData<List<EventoDb>>()

    private var debouncePeriod: Long = 500
    private var searchJob: Job? = null

    init {
        eventsRepository = EventsRepository(EventsRoomDb.getDatabase(application))
        Log.d("events", "${eventsRepository.events}")
        readEventData = eventsRepository.events
        Log.d("pippo", "$readEventData")
        //refreshDataFromRepository()
    }

    fun getDataFromRemote() {
        viewModelScope.launch(Dispatchers.IO){
            eventsRepository.getDataFromRemote()
        }
    }

//    fun filterEvents(titleCat :String) : LiveData<List<EventoDb>> {
//        val lista = eventsRepository.filterCat(titleCat)
//        return lista
//    }



    fun onFilterQuery(titleCat: String) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(debouncePeriod)
                if (titleCat != "") {
                    fetchEventByQuery(titleCat)
                }
            }
    }

    private fun fetchEventByQuery(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val filtered = eventsRepository.filterCat(query)
            filterEventsLiveData.postValue(filtered)
        }
    }
}
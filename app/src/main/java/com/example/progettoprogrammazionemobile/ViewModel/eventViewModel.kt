package com.example.progettoprogrammazionemobile.ViewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.appericolo.ui.preferiti.contacts.database.EventoDb
import com.example.appericolo.ui.preferiti.contacts.database.EventsRoomDb
import com.example.progettoprogrammazionemobile.Repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class eventViewModel(application: Application) : AndroidViewModel(application) {

    var readEventData: LiveData<List<EventoDb>>
    private val eventsRepository: EventsRepository
    lateinit var imageUri: Uri

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


    /* ------------------------------
    FUN TO SAVE EVENT
    ---------------------------------
     */
    fun saveEvento(model: EventoDb) {
        viewModelScope.launch(Dispatchers.IO) {
            eventsRepository.insert(model, imageUri)
        }
    }
    fun setUri(imageUri: Uri) {
        this.imageUri = imageUri
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
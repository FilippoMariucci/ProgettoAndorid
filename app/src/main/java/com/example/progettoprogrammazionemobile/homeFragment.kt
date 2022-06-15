package com.example.progettoprogrammazionemobile

import android.graphics.Bitmap
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.AdapterRV.AdapterImageEvent
import com.example.progettoprogrammazionemobile.AdapterRV.ImageAdapter
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.example.progettoprogrammazionemobile.model.category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class homeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var eventsRec: RecyclerView
    private lateinit var eventList: ArrayList<Evento>
    private lateinit var dbRef: DatabaseReference
    private val dettaglioEvento = dettaglio_evento()
    val imageRef = Firebase.storage.reference
    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val mapEventsBitMap = mutableMapOf<String, Bitmap>()
    val imagesUrl = listOf<String>()


    private var _binding : com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //getlistImage()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var categoryimgs = listOf<category>(
            category(  R.drawable.ic_icons8_montagna,"Adventure"),
            category(  R.drawable.ic_icons8_illustrator,"Art"),
            category(  R.drawable.ic_icons8_musica__1_,"Concert"),
            category(  R.drawable.ic_icons8_sports,"Sport"),
            category(  R.drawable.ic_icons8_photo,"Photo"),
            category(  R.drawable.ic_icons8_carte_da_gioco,"Games"),
            category(  R.drawable.ic_icons8_festa_di_ballo,"Party"))



        val recyclerView = binding.categories
        var AdapterCategories = ImageAdapter(categoryimgs)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = AdapterCategories

        AdapterCategories.setOnItemClickListener(object : ImageAdapter.onItemClickListener{
            override fun onItemClick(titleCat: String) {
                Toast.makeText(requireContext(), "${titleCat}",Toast.LENGTH_LONG).show()
                if(getEventsData(titleCat)) {listFiles()}
            }
        })


        eventsRec = binding.rvEvents
        val myLinearLayoutManager = object : LinearLayoutManager(requireContext()) {
              override fun canScrollVertically(): Boolean {
              return false
           }
        }
        eventsRec.layoutManager = myLinearLayoutManager
        eventsRec.setHasFixedSize(true)
        eventList = arrayListOf<Evento>()

        //getImages("-N4WbFcIhZz-3BAJnuDa")

        if(getEventsData("")){ listFiles() }

    }


    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        try{
            val images = imageRef.child("Users/$uid/").listAll().await()
            val imageUrls = mutableListOf<String>()
            for(i in images.items){
                val url = i.downloadUrl.await()
                imageUrls.add(url.toString())
            }
            withContext(Dispatchers.Main){
                val imageAdapter = AdapterImageEvent(imageUrls, eventList)
                eventsRec.apply {
                    adapter = imageAdapter

                }


                imageAdapter.setOnItemClickListener(object : AdapterImageEvent.onItemClickListener{
                    override fun onItemClick(idevento: String, url_image: String) {
                        Toast.makeText(requireContext(), "Clicked on item $idevento", Toast.LENGTH_SHORT).show()
                        go_away(idevento, url_image)
                    }

                    override fun skipEvent(posizione: String, sizeList: Int) {
                        val actualPosition = Integer.parseInt(posizione)
                        Toast.makeText(requireContext(), "$actualPosition", Toast.LENGTH_SHORT).show()
                        val nextPosition = actualPosition + 1
                        if(nextPosition < sizeList) {
                            eventsRec.layoutManager?.scrollToPosition(nextPosition)
                        }
                    }
                })


            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getEventsData(categoria : String) : Boolean{

        dbRef = FirebaseDatabase.getInstance().getReference("Evento")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()
                if (snapshot.exists()) {
                    for (eventSnap in snapshot.children) {
                        // prendo foto dallo storage

                        val eventoSingolo = eventSnap.getValue(Evento::class.java)

                        // scelgo gli eventi se c'è stato il clic sulla categoria
                        if (categoria.length != 0) {
                            if (eventoSingolo != null) {
                                if (eventoSingolo.categoria == categoria) {
                                    eventList.add(eventoSingolo!!)
                                }
                            }
                        } else {
                            eventList.add(eventoSingolo!!)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        if(eventList.size > 0) return true
        else return false
    }

    fun go_away(idevento: String, url: String){
        val bundle = Bundle()
        bundle.putString("idEvento", idevento)
        bundle.putString("url_image", url)
        dettaglioEvento.arguments = bundle
        if(isAdded)  fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, dettaglioEvento)?.commit()
    }
}





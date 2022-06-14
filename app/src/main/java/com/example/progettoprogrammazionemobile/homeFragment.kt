package com.example.progettoprogrammazionemobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.AdapterRV.Adapter
import com.example.progettoprogrammazionemobile.AdapterRV.EventsAdapter
import com.example.progettoprogrammazionemobile.AdapterRV.ImageAdapter
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.example.progettoprogrammazionemobile.model.category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import kotlin.io.*

class homeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var eventsRec: RecyclerView
    private lateinit var eventList: ArrayList<Evento>
    private lateinit var dbRef: DatabaseReference
    private val dettaglioEvento = dettaglio_evento()
    private lateinit var storageRef : StorageReference

    private var _binding : com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var categoryimgs = listOf<category>(
            category(  R.drawable.adventurecategory,"Adventure"),
            category(  R.drawable.artcategory,"Art"),
            category(  R.drawable.concertcategory,"Concert"),
            category(  R.drawable.sportcategory,"Sport"),
            category(  R.drawable.photocategory,"Photo"),
            category(  R.drawable.rolegamecategory,"Role Games"),
            category(  R.drawable.partycategory,"Party"))



        val recyclerView = binding.categories
        var AdapterCategories = ImageAdapter(categoryimgs)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = AdapterCategories

        AdapterCategories.setOnItemClickListener(object : ImageAdapter.onItemClickListener{
            override fun onItemClick(titleCat: String) {
                Toast.makeText(requireContext(), "${titleCat}",Toast.LENGTH_LONG).show()
                getEventsData(titleCat)
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

        getEventsData("")

    }

    private fun getEventsData(categoria : String){
        eventsRec.visibility = View.GONE

        val auth = FirebaseAuth.getInstance()
        var prova = arrayListOf<String>()
        val mapEventsBitMap = mutableMapOf<String, Bitmap>()
        var uid = auth.currentUser?.uid.toString()
        storageRef = FirebaseStorage.getInstance().getReference("Users/$uid")
        dbRef = FirebaseDatabase.getInstance().getReference("Evento")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()
                if(snapshot.exists()){
                    for(eventSnap in snapshot.children){
                        // prendo foto dallo storage

                        var eventoSingolo = eventSnap.getValue(Evento::class.java)
                        var id_evento_for_foto = eventoSingolo?.id_evento
                        var islandRef = storageRef.child("$id_evento_for_foto.jpg")
                        val localFile = File.createTempFile(uid, "jpg")

                        islandRef.getFile(localFile).addOnSuccessListener {
                            prova.add("pipo")
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), "FALLIMENTO", Toast.LENGTH_LONG).show()
                        }
//                            var localfile = File.createTempFile(uid, "")
//                            path.getFile(localfile).addOnSuccessListener {
//                                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
//                                mapEventsBitMap.put("$id_evento_for_foto", bitmap)
//                            }.addOnFailureListener {
//                                Toast.makeText(requireContext(), "FALLIMENTO", Toast.LENGTH_LONG).show()
//                            }

                                // creo l'evento con i dati del db



                                // scelgo gli eventi se c'è stato il clic sulla categoria
                                if(categoria.length!=0) {
                                    if (eventoSingolo != null) {
                                        if(eventoSingolo.categoria == categoria) {
                                            eventList.add(eventoSingolo!!)
                                        }
                                    }
                                }
                                else {eventList.add(eventoSingolo!!)}
                            }
                    }

                    // display to recycler view
                    val eAdapter = EventsAdapter(eventList, mapEventsBitMap)
                    eventsRec.adapter = eAdapter

                    eAdapter.setOnItemClickListener(object : EventsAdapter.onItemClickListener{
                        override fun onItemClick(idevento: String) {
                            Toast.makeText(requireContext(), "Clicked on item $idevento", Toast.LENGTH_SHORT).show()
                            go_away(idevento)
                        }

                        override fun skipEvent(posizione: String) {
                            val actualPosition = Integer.parseInt(posizione)
                            Toast.makeText(requireContext(), "$actualPosition", Toast.LENGTH_SHORT).show()
                            val nextPosition = actualPosition + 1
                            eventsRec.layoutManager?.scrollToPosition(nextPosition)
                        }
                    })

                    eventsRec.visibility = View.VISIBLE
                }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



    fun go_away(idevento: String){
        val bundle = Bundle()
        bundle.putString("idEvento", idevento)
        dettaglioEvento.arguments = bundle
        if(isAdded)  fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, dettaglioEvento)?.commit()
    }
}





package com.example.progettoprogrammazionemobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
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
import com.google.firebase.database.*

class homeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var eventsRec: RecyclerView
    private lateinit var eventList: ArrayList<Evento>
    private lateinit var dbRef: DatabaseReference
    private val dettaglioEvento = dettaglio_evento()

    private var _binding : FragmentHomeBinding? = null
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
        var mimmo = ImageAdapter(categoryimgs)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = mimmo

        mimmo.setOnItemClickListener(object : ImageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), "${position}",Toast.LENGTH_LONG).show()
            }
        })


        eventsRec = binding.rvEvents
        eventsRec.layoutManager = LinearLayoutManager(this.requireContext())
        eventsRec.setHasFixedSize(true)
        eventList = arrayListOf<Evento>()

        getEventsData()

    }

    private fun getEventsData(){
        eventsRec.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Evento")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()
                if(snapshot.exists()){
                    for(eventSnap in snapshot.children){
                        val eventoSingolo = eventSnap.getValue(Evento::class.java)
                        eventList.add(eventoSingolo!!)
                    }
                    val eAdapter = EventsAdapter(eventList)
                    eventsRec.adapter = eAdapter

                    eAdapter.setOnItemClickListener(object : EventsAdapter.onItemClickListener{
                        override fun onItemClick(position: String) {
                            Toast.makeText(requireContext(), "Clicked on item $position", Toast.LENGTH_SHORT).show()
                            go_away(position)
                        }

                    })

                    eventsRec.visibility = View.VISIBLE
                }
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





package com.example.progettoprogrammazionemobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.AdapterRV.occasioniAccettateAdapter
import com.example.progettoprogrammazionemobile.databinding.FragmentOccasioniAccettateBinding
import com.example.progettoprogrammazionemobile.databinding.FragmentOccasioniCreateBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.example.progettoprogrammazionemobile.model.Partecipazione
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class occasioni_accettate : Fragment() {
    private lateinit var AcceptedEventsRec : RecyclerView
    private lateinit var AcceptedEventsUser : ArrayList<Evento>
    private lateinit var PartecipazioneUser : ArrayList<Partecipazione>
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    private var _binding: FragmentOccasioniAccettateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        // Inflate the layout for this fragment
        _binding = FragmentOccasioniAccettateBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getEventsKey()

        AcceptedEventsRec = binding.recyclerOccasioniAccettate
        AcceptedEventsRec.layoutManager = LinearLayoutManager(this.requireContext())
        AcceptedEventsRec.setHasFixedSize(true)
        AcceptedEventsUser = arrayListOf<Evento>()
        getEventsKey()

    }

    private fun getEventsKey() {
        val PartecipazioneDb = FirebaseDatabase.getInstance().getReference("Partecipazione").
        addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var pipetto = snapshot.children
                pipetto.forEach{
                    val mario = it.child("id_partecipante").getValue() as ArrayList<String>
                    val size = mario.size
                    for(i in 0..size-1){
                        if (mario[i] == uid){
                            var key = it.key.toString()
                            getUserFavouriteEvent(key)

                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun getUserFavouriteEvent(key : String){
        AcceptedEventsRec.visibility = View.GONE

        val Eventi = FirebaseDatabase.getInstance().getReference("Evento").orderByChild("id_evento").equalTo(key)
        Log.d("eventi", "$Eventi")
        Eventi.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                AcceptedEventsUser.clear()
                if(snapshot.exists()){
                    for(eventSnap in snapshot.children){
                        val singoloEvento = eventSnap.getValue(Evento :: class.java)
                        Log.d("singoloevento","$singoloEvento")
                        AcceptedEventsUser.add(singoloEvento!!)
                    }
                }
                val adapter = occasioniAccettateAdapter(AcceptedEventsUser)
                AcceptedEventsRec.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        AcceptedEventsRec.visibility = View.VISIBLE
    }

}
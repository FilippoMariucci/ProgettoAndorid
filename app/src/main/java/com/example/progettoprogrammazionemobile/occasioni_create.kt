package com.example.progettoprogrammazionemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.AdapterRV.occasioniCreateAdapter
import com.example.progettoprogrammazionemobile.databinding.FragmentOccasioniCreateBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class occasioni_create : Fragment() {

    private lateinit var CreateEventsRec : RecyclerView
    private lateinit var createdEvents : ArrayList<Evento>
    private lateinit var dbRef : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uid : String

    private var _binding : FragmentOccasioniCreateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOccasioniCreateBinding.inflate(inflater, container, false, )
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CreateEventsRec = binding.recyclerOccasioniCreate
        CreateEventsRec.layoutManager = LinearLayoutManager(this.requireContext())
        CreateEventsRec.setHasFixedSize(true)

        createdEvents = arrayListOf<Evento>()
        getUserEvents()

        binding.btnAddEvent.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, crea_occasione())?.commit()
        }
    }

    private fun getUserEvents() {
        CreateEventsRec.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().reference
        var dbUserOffer = dbRef.child("Evento").orderByChild("userId").equalTo(uid)
        dbUserOffer.addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        createdEvents.clear()
                        if (snapshot.exists()){
                            for  (eventSnap in snapshot.children){
                                val singoloEvento = eventSnap.getValue(Evento:: class.java)
                                createdEvents.add(singoloEvento!!)
                            }
                            val adapter = occasioniCreateAdapter(createdEvents)
                            CreateEventsRec.adapter = adapter
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
                CreateEventsRec.visibility = View.VISIBLE
            }
        }

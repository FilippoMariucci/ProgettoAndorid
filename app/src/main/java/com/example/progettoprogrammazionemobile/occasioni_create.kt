package com.example.progettoprogrammazionemobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazionemobile.AdapterRV.AdapterImageEvent
import com.example.progettoprogrammazionemobile.AdapterRV.occasioniCreateAdapter
import com.example.progettoprogrammazionemobile.ViewModel.EventoViewModel
import com.example.progettoprogrammazionemobile.databinding.FragmentOccasioniCreateBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class occasioni_create : Fragment() {

    private lateinit var CreateEventsRec: RecyclerView
    private lateinit var createdEvents: ArrayList<Evento>
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    private var _binding: FragmentOccasioniCreateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOccasioniCreateBinding.inflate(inflater, container, false,)
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
        listFiles(createdEvents, CreateEventsRec)

        binding.btnAddEvent.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, crea_occasione())
                ?.commit()
        }
    }

    private fun getUserEvents() {
        CreateEventsRec.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().reference
        var dbUserOffer = dbRef.child("Evento").orderByChild("userId").equalTo(uid)
        dbUserOffer.addValueEventListener(object : ValueEventListener {
            val vm = EventoViewModel()
            override fun onDataChange(snapshot: DataSnapshot) {
                createdEvents.clear()
                if (snapshot.exists()) {
                    for (eventSnap in snapshot.children) {
                        val singoloEvento = eventSnap.getValue(Evento::class.java)
                        createdEvents.add(singoloEvento!!)
                    }

//                            val adapter = occasioniCreateAdapter(createdEvents)
//                            CreateEventsRec.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        CreateEventsRec.visibility = View.VISIBLE
    }

    private fun listFiles(list: ArrayList<Evento>, rec: RecyclerView) = CoroutineScope(
        Dispatchers.IO
    ).launch {
        val storageReference = Firebase.storage.reference
        try {
            val images = storageReference.child("Users/").listAll().await()
            val imageUrls = mutableListOf<String>()
            val prova = images.items
            for (i in images.items) {
                val url = i.downloadUrl.await()
                val url_to_check = i.toString().substringAfterLast('/').substringBefore('.')

                for (y in list) {
                    if (url_to_check == y.id_evento) imageUrls.add(url.toString())
                }
//            val url = i.downloadUrl.await()
//            Log.d("pr", "$url")
//            imageUrls.add(url.toString())
            }
            withContext(Dispatchers.Main) {
                val imageAdapter = occasioniCreateAdapter(list, imageUrls)
                rec.apply {
                    adapter = imageAdapter
                }

            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.d("eccezione_immagini", e.message.toString())
            }
            //Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }
}


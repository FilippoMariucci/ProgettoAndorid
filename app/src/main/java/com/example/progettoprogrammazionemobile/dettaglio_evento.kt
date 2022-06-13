package com.example.progettoprogrammazionemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.progettoprogrammazionemobile.databinding.FragmentDettaglioEventoBinding
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.database.*


class dettaglio_evento : Fragment() {

    private lateinit var idEvento : String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var evento : Evento
    private var _binding : FragmentDettaglioEventoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = this.arguments
        val argsEvento = args?.get("idEvento")
        idEvento = argsEvento.toString()
        _binding = FragmentDettaglioEventoBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_dettaglio_evento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("Evento")
        getEventData()
    }

    private fun getEventData() {
        databaseReference.child(idEvento).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                evento = snapshot.getValue(Evento :: class.java)!!
                binding.titoloEventoDett.setText(evento.titolo)
                binding.fotoEvento.setImageResource(R.drawable.rico)
                binding.dataDett.setText(evento.data_evento)
                binding.indirizzoDett.setText(evento.indirizzo)
                binding.linguaEventoDett.setText(evento.lingue)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}
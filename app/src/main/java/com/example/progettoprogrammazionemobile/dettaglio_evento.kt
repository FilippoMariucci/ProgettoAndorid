package com.example.progettoprogrammazionemobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.progettoprogrammazionemobile.databinding.FragmentDettaglioEventoBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.example.progettoprogrammazionemobile.model.Partecipazione
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class dettaglio_evento : Fragment() {

    private lateinit var idEvento : String

    private lateinit var databaseReferenceUser: DatabaseReference
    private lateinit var databaseReferencePartecipazione: DatabaseReference

    private lateinit var listPartecipanti : ArrayList<String>

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
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Evento")
        getEventData()
        binding.buttonPartecipoDett.setOnClickListener{paretecipaEvento()}
    }


    private fun getEventData() {
        databaseReferenceUser.child(idEvento).addValueEventListener(object : ValueEventListener{
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

    private fun paretecipaEvento(){
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString().trim()
        val id_evento = evento.id_evento
        val id_creatore = evento.userId
        val id_partecipante = uid

        listPartecipanti = ArrayList<String>()

        //listPartecipanti.size <= evento.n_persone

        databaseReferencePartecipazione = FirebaseDatabase.getInstance().getReference("Partecipazione")

        var loadArray = FirebaseDatabase.getInstance().getReference("Partecipazione").child(idEvento)
        loadArray.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listPartecipanti.clear()
                if (snapshot.child("id_partecipante").getValue() as ArrayList <String> != null) {
                    var nome = snapshot.child("id_partecipante").getValue() as ArrayList<String>
                    val size = nome.size
                    for (i in 0..size - 1) {
                        listPartecipanti.add(nome[i])
                    }
                }
                if (id_creatore.equals(id_partecipante) || listPartecipanti.contains(id_partecipante)) Toast.makeText(requireContext(), "You can't apply for this event", Toast.LENGTH_SHORT).show()
                else {
                    listPartecipanti.add(id_partecipante)
                    val partecipazione = Partecipazione(id_creatore, listPartecipanti)
                    if (id_evento != null) {
                        databaseReferencePartecipazione.child(id_evento).setValue(partecipazione)
                            .addOnSuccessListener { Toast.makeText(requireContext(), "Your application for this event was succesful!", Toast.LENGTH_LONG).show() }.
                            addOnFailureListener { Toast.makeText(requireContext(), "Sorry we got troubles!", Toast.LENGTH_LONG).show()
                            }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}
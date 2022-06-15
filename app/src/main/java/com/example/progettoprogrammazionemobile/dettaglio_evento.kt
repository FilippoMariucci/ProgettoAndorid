package com.example.progettoprogrammazionemobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.progettoprogrammazionemobile.databinding.FragmentDettaglioEventoBinding
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.example.progettoprogrammazionemobile.model.Partecipazione
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.event_item.view.*


class dettaglio_evento : Fragment() {

    private lateinit var idEvento : String

    private lateinit var databaseReferenceUser: DatabaseReference
    private lateinit var databaseReferencePartecipazione: DatabaseReference

    private lateinit var listPartecipanti : ArrayList<String>

    private lateinit var evento : Evento
    private var _binding : FragmentDettaglioEventoBinding? = null
    private val binding get() = _binding!!
    private lateinit var urlImageEvento : String

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = this.arguments
        val argsEvento = args?.get("idEvento")
        urlImageEvento = args?.get("url_image") as String
        idEvento = argsEvento.toString()
        _binding = FragmentDettaglioEventoBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_dettaglio_evento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Evento")
        getEventData()
        //binding.buttonPartecipoDett.setOnClickListener{paretecipaEvento()}
    }


    private fun getEventData() {
        databaseReferenceUser.child(idEvento).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                evento = snapshot.getValue(Evento :: class.java)!!
                Glide.with(requireContext()).load(urlImageEvento).into(binding.fotoEvento)

                binding.titoloEventoDett.setText(evento.titolo)
                //binding.fotoEvento.setImageResource(R.drawable.rico)
                binding.dataDett.setText(evento.data_evento)
                binding.indirizzoDett.setText(evento.indirizzo)
                binding.linguaEventoDett.setText(evento.lingue)
                binding.categoriaEventoDett.setText(evento.categoria)
                binding.cittaDett.setText(evento.citta)
                binding.indirizzoDett.setText(evento.indirizzo)
                binding.descEventoDett.setText(evento.descrizione)

                val npersone = evento.n_persone?.toInt()
                FirebaseDatabase.getInstance().getReference("Partecipazione").child(idEvento).get().addOnSuccessListener{
                    val listPartecipanti =  it.child("id_partecipante").getValue() as ArrayList<String>
                    val size = listPartecipanti.size
                    var partecipanti = 0
                    for (i in 0..size-1) {
                        if (listPartecipanti[i] != null) partecipanti += 1
                    }
                    val persone = npersone?.minus(partecipanti)
                    if (persone != null) {
                        binding.npersone.setText(persone.toString())
                    }
                    if(persone == 0) {
                        binding.buttonPartecipoDett.visibility = View.INVISIBLE
                        binding.fullEventoText.visibility = View.VISIBLE
                        binding.fullEventoText.setText("Raggiunto il numero massimo di partecipanti")
                    }
                    else{
                        binding.buttonPartecipoDett.setOnClickListener{
                            partecipaEvento()
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun partecipaEvento(){
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString().trim()
        val id_evento = evento.id_evento
        val id_creatore = evento.userId
        val id_partecipante = uid

        listPartecipanti = ArrayList<String>()

        //listPartecipanti.size <= evento.n_persone
        if(id_creatore.equals(id_partecipante)) {
            Toast.makeText(requireContext(), "You can't apply for your own event", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            databaseReferencePartecipazione = FirebaseDatabase.getInstance().getReference("Partecipazione")

            var loadArray = FirebaseDatabase.getInstance().getReference("Partecipazione").child(idEvento)
            loadArray.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listPartecipanti.clear()
                    try {
                            var nome = snapshot.child("id_partecipante").getValue() as ArrayList<String>
                            val size = nome.size
                            for (i in 0..size - 1) {
                                listPartecipanti.add(nome[i])
                            }
                    }
                    catch (e:Exception) {
                        Toast.makeText(requireContext(), "Sei il primo a partecipare!", Toast.LENGTH_SHORT).show()
                    }
                    if (listPartecipanti.contains(id_partecipante)) Toast.makeText(requireContext(), "You can't apply for this event", Toast.LENGTH_SHORT).show()
                    else {
                        listPartecipanti.add(id_partecipante)
                        val partecipazione = Partecipazione(id_creatore, listPartecipanti)
                        if (id_evento != null) {
                            databaseReferencePartecipazione.child(id_evento)
                                .setValue(partecipazione)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Your application for this event was succesful!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Sorry we got troubles!",
                                        Toast.LENGTH_LONG
                                    ).show()
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

}
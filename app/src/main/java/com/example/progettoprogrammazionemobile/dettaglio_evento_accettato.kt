package com.example.progettoprogrammazionemobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.progettoprogrammazionemobile.ViewModel.EventoViewModel
import com.example.progettoprogrammazionemobile.databinding.FragmentDettaglioEventoAccettatoBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.example.progettoprogrammazionemobile.model.User
import com.google.firebase.database.*
import kotlin.properties.Delegates

class dettaglio_evento_accettato : Fragment() {
    private var viewEvento = EventoViewModel()
    private lateinit var idEvento: String
    private lateinit var stringa : String
    private lateinit var databaseRefEvent: DatabaseReference
    private lateinit var databaseRefUser: DatabaseReference
    private lateinit var evento: Evento
    private lateinit var user: User
    private lateinit var userName: String
    private lateinit var userSurname: String

    private var _binding: FragmentDettaglioEventoAccettatoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = this.arguments
        val argsEventoAccettato = args?.get("idEventoAccettato")
        userName = ""
        userSurname = ""
        idEvento = argsEventoAccettato.toString()
        _binding = FragmentDettaglioEventoAccettatoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRefEvent = FirebaseDatabase.getInstance().getReference("Evento")
        databaseRefUser = FirebaseDatabase.getInstance().getReference("Users")
        getEventData()
    }

    private fun getEventData() {
        databaseRefEvent.child(idEvento).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                evento = snapshot.getValue(Evento::class.java)!!
                databaseRefUser.child(evento.userId.toString()).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        user = snapshot.getValue(User::class.java)!!
                        userName = user.name.toString()
                        userSurname = user.surname.toString()
                        stringa = "$userName $userSurname"
                        Log.d("Strigna", "$stringa")
                        binding.titoloDettaglioAccettato.setText(evento.titolo)
                        binding.prezzodettaglioAccettato.setText(evento.costo + "€")

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
                                binding.personeDettaglioAccettato.setText(persone.toString())
                            }
                        }

                        binding.indirizzoDettaglioAccettato.setText(evento.indirizzo)
                        binding.cittaDettaglioAccettato.setText(evento.citta)
                        binding.dataDettaglioAccettato.setText(evento.data_evento)
                        binding.nomeUtenteDettaglioAccettato.setText(stringa)
                        binding.categoriaDettaglioAccettato.setText(evento.categoria)
                        binding.lingueDettaglioAccettato.setText(evento.lingue)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
            }
        })
    }
}


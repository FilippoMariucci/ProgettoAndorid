package com.example.progettoprogrammazionemobile

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.progettoprogrammazionemobile.AdapterRV.EventsAdapter
import com.example.progettoprogrammazionemobile.ViewModel.EventoViewModel
import com.example.progettoprogrammazionemobile.databinding.FragmentCreaOccasioneBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.lang.StringBuilder


class crea_occasione : Fragment(R.layout.fragment_crea_occasione) {


    private var _binding: FragmentCreaOccasioneBinding? = null
    private val binding get() = _binding!!

    private val languages = listOf<String>("English", "Italian", "Spanish", "Russian", "French")


    private val viewModelEvento: EventoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout XML file and return a binding object instance
        _binding= FragmentCreaOccasioneBinding.inflate(inflater, container, false)

        val languageAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, languages)
        binding.lingueEvento.setAdapter(languageAdapter)
        binding.lingueEvento.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())




//        var getData = object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var sb = StringBuilder()
//                for(i in snapshot.children){
//                    var event_titolo = i.child("titolo").getValue()
//                    var event_categoria = i.child("categoria").getValue()
//                    sb.append("${i.key}  $event_titolo $event_categoria")
//                }
//                binding.TestoMagico.setText(sb)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        }
//
//        database.addValueEventListener(getData)
//        database.addListenerForSingleValueEvent(getData)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //reference = FirebaseDatabase.getInstance().getReference("Evento")
        binding.btnaddEvento.setOnClickListener{ this.saveEvento() }
        //this.loadEventsFromDb()
        //viewModelEvento.loadEventsFromDb()

    }


    private fun saveEvento(){

        val titolo_evento = binding.titoloEvento.text.toString().trim()
        val descrizione_evento = binding.descrizioneEvento.text.toString().trim()
        val lingue_evento = binding.lingueEvento.text.toString().trim()
        val indirizzo_evento = binding.indirizzoEvento.text.toString().trim()
        val npersone_evento = binding.npersoneEvento.text.toString().trim()
        val costo_evento = binding.costoEvento.text.toString().trim()
        val citta_evento = binding.cittaEvento.text.toString().trim()
        val data_evento = binding.dataEvento.text.toString().trim()
        val foto_evento = binding.fotoEvento.text.toString().trim()
        val categoria_evento = binding.categorieEvento.text.toString().trim()

        if(titolo_evento.isEmpty()){
            Toast.makeText(this.context, "pppppp", Toast.LENGTH_LONG).show()
        }

        val model= Evento(titolo_evento, descrizione_evento, lingue_evento,
            categoria_evento, citta_evento, indirizzo_evento, data_evento, costo_evento,
            npersone_evento, foto_evento)
        viewModelEvento.saveEvent(model)



    }

}
package com.example.progettoprogrammazionemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.progettoprogrammazionemobile.databinding.FragmentCreaOccasioneBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class crea_occasione : Fragment(R.layout.fragment_crea_occasione) {


    private var _binding: FragmentCreaOccasioneBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    private lateinit var reference: DatabaseReference

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        database=FirebaseDatabase.getInstance()
//        reference=database.getReference("Evento")
//
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout XML file and return a binding object instance
         _binding= FragmentCreaOccasioneBinding.inflate(inflater, container, false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reference = FirebaseDatabase.getInstance().getReference("Evento")
        binding.btnaddEvento.setOnClickListener{ this.saveEvento() }
    }



//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Set the viewModel for data binding - this allows the bound layout access
//        // to all the data in the VieWModel
////        binding.gameViewModel = viewModel
////        binding.maxNoOfWords = MAX_NO_OF_WORDS
//        // Specify the fragment view as the lifecycle owner of the binding.
//        // This is used so that the binding can observe LiveData updates
////        binding.lifecycleOwner = viewLifecycleOwner
//
//        // Setup a click listener for the Submit and Skip buttons.
//
//    }

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

        val id_evento = "prova1"

        val model= Evento(titolo_evento, descrizione_evento, lingue_evento,
            categoria_evento, citta_evento, indirizzo_evento, data_evento, costo_evento,
            npersone_evento, foto_evento)


        if (id_evento != null) {
            reference.child(id_evento).setValue(model)
                .addOnCompleteListener{
                    Toast.makeText(this.context, "grazie signore che ci hai dato il calcio", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(this.context, "errore", Toast.LENGTH_LONG).show()
                }
        }

    }
        }

package com.example.progettoprogrammazionemobile

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.progettoprogrammazionemobile.ViewModel.EventoViewModel
import com.example.progettoprogrammazionemobile.databinding.FragmentModificaOccasioneBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.database.*
import kotlin.concurrent.fixedRateTimer


class modifica_occasione : Fragment(R.layout.fragment_modifica_occasione), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var evento: Evento
    private lateinit var idEvento : String
    private lateinit var databaseRef : DatabaseReference
    private var _binding : FragmentModificaOccasioneBinding? = null
    private val viewModelEvento: EventoViewModel by activityViewModels()

    private val binding get() = _binding!!
    var array_date_time = arrayListOf<Int>()
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val arguments = this.arguments
        val argsEvento = arguments?.get("idEvento")
        idEvento = argsEvento.toString()
        _binding = FragmentModificaOccasioneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.InputDataEventoModifica.setOnClickListener(View.OnClickListener {
            array_date_time = viewModelEvento.getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, array_date_time.get(2), array_date_time.get(1), array_date_time.get(0)).show()
             })
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayofMonth: Int) {
        savedDay = dayofMonth
        savedMonth = month
        savedYear = year

        array_date_time = arrayListOf<Int>()
        array_date_time = viewModelEvento.getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, array_date_time.get(3), array_date_time.get(4), true).show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        savedHour = hour
        savedMinute = minute

        binding.textDataEventoModificato.text = "$savedDay-$savedMonth-$savedYear at $savedHour:$savedMinute"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseRef = FirebaseDatabase.getInstance().getReference("Evento").child(idEvento)
        getEventData()
    }



    private fun getEventData() {
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                evento = snapshot.getValue(Evento::class.java)!!
                Log.d("evento", "$evento")
                val languages = resources.getStringArray(R.array.languages)
                val categories = resources.getStringArray(R.array.categories)
                val arrayLanguagesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
                val arrayCategoriesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)

                binding.titoloModificaEvento.editText?.setText( evento.titolo)
                binding.DescrizionemodificaEvento.editText?.setText(evento.descrizione)
                binding.cittaModificaEvento.editText?.setText(evento.citta)
                binding.autoCompleteLanguagesModifica.setText(evento.lingue)
                binding.autoCompleteLanguagesModifica.setAdapter(arrayLanguagesAdapter)
                binding.autoCompleteCategoriesModifica.setText(evento.categoria)
                binding.autoCompleteCategoriesModifica.setAdapter(arrayCategoriesAdapter)
                binding.indirizzoModificaEvento.editText?.setText(evento.indirizzo)
                binding.npersoneEventoModifica.editText?.setText(evento.n_persone)
                binding.prezzoEventoModifica.editText?.setText(evento.costo)

                binding.textDataEventoModificato.setText(evento.data_evento)

                binding.btnModifyevento.setOnClickListener{
                    val titolo = binding.titoloModificaEvento.editText?.text.toString()
                    val descrizione = binding.DescrizionemodificaEvento.editText?.text.toString()
                    val citta = binding.cittaModificaEvento.editText?.text.toString()
                    val categoria  = binding.autoCompleteCategoriesModifica.text.toString()
                    val indirizzo = binding.indirizzoModificaEvento.editText?.text.toString()
                    val nPersone  = binding.npersoneEventoModifica.editText?.text.toString()
                    val costo = binding.prezzoEventoModifica.editText?.text.toString()
                    val lingua = binding.autoCompleteLanguagesModifica.text.toString()
                    val data = binding.textDataEventoModificato.text.toString()
                    updateEvent(titolo, descrizione, citta, categoria, indirizzo, nPersone, costo, lingua, data)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun updateEvent( titolo : String, descrizione : String, citta : String, categoria : String,
                            indirizzo : String, nPersone : String, costo : String, lingua : String,
                            data : String,) {
        val event = mapOf<String, String >(
            "titolo" to titolo,
            "categoria" to categoria,
            "citta" to citta,
            "costo" to costo,
            "data_evento" to data,
            "descrizione" to descrizione,
            "indirizzo" to indirizzo,
            "lingue" to lingua,
            "n_persone" to nPersone
        )
        databaseRef.updateChildren(event).addOnSuccessListener {
            Toast.makeText(requireContext(), " Evento Modificato con successo", Toast.LENGTH_SHORT).show()
            fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, occasioni_create())?.commit()
        }
    }
}
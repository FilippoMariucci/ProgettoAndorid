package com.example.progettoprogrammazionemobile

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.progettoprogrammazionemobile.AdapterRV.EventsAdapter
import com.example.progettoprogrammazionemobile.ViewModel.EventoViewModel
import com.example.progettoprogrammazionemobile.databinding.FragmentCreaOccasioneBinding
import com.example.progettoprogrammazionemobile.model.Evento
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class crea_occasione : Fragment(R.layout.fragment_crea_occasione), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {



    private lateinit var auth: FirebaseAuth
    private lateinit var uid : String
    private var _binding: FragmentCreaOccasioneBinding? = null
    private val binding get() = _binding!!
    //private lateinit var binding: FragmentCreaOccasioneBinding
    private lateinit var  databaseReference: DatabaseReference
    private lateinit var  storageReference: StorageReference
    private val mStorageRef = FirebaseStorage.getInstance().reference

    private lateinit var imageUri: Uri
    private  var button : Button ?= null
    private  var imageView: ImageView ?= null

    private var packageName = BuildConfig.APPLICATION_ID

    private val viewModelEvento: EventoViewModel by activityViewModels()
    var array_date_time = arrayListOf<Int>()
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        val languages = resources.getStringArray(R.array.languages)
        val categories = resources.getStringArray(R.array.categories)
        val arrayLanguagesAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        val arrayCategoriesAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
        binding.autoCompleteCategories.setAdapter(arrayCategoriesAdapter)
        binding.autoCompleteLanguages.setAdapter(arrayLanguagesAdapter)



        binding.InputDataEvento.setOnClickListener(View.OnClickListener {
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

        binding.textDateEvento.text = "$savedDay-$savedMonth-$savedYear at $savedHour:$savedMinute"
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout XML file and return a binding object instance
        _binding= FragmentCreaOccasioneBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()


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


        button = getView()?.findViewById(R.id.scegliImmagine)
        imageView = getView()?.findViewById(R.id.immagine)

        button?.setOnClickListener {
            pickImagegallery()
        }

        binding.btnaddEvento.setOnClickListener{ this.saveEvento() }
        //this.loadEventsFromDb()
        //viewModelEvento.loadEventsFromDb()




        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.btnaddEvento.setOnClickListener{

            Toast.makeText(this.requireContext(), "chicco gay$packageName", Toast.LENGTH_SHORT).show()

            this.uploadEventPicture()
            this.saveEvento()

        }


    }

    private fun pickImagegallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView?.setImageURI(data?.data)
            imageUri = data?.data!!
        }
    }


    private fun uploadEventPicture(){



        storageReference = FirebaseStorage.getInstance().getReference("Users/"+ auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {

            Toast.makeText(this.requireContext(), "immagine ok", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this.requireContext(), "immagine failata", Toast.LENGTH_SHORT).show()
        }
    }





    private fun saveEvento(){


        val idEvento = ""
        val titolo_evento = binding.titoloEvento.editText?.text.toString().trim()
        val descrizione_evento = binding.DescrizioneEvento.editText?.text.toString().trim()
        val lingue_evento = binding.autoCompleteLanguages.text.toString().trim()
        val indirizzo_evento = binding.indirizzoEvento.editText?.text.toString().trim()
        val npersone_evento = binding.npersoneEvento.editText?.text.toString().trim()
        val costo_evento = binding.prezzoEvento.editText?.text.toString().trim()
        val citta_evento = binding.CittaEvento.editText?.text.toString().trim()
        val data_evento = binding.dataEvento.editText?.text.toString().trim()
        val foto_evento = imageUri.toString().trim()
        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        val w: Int = 1
        val h: Int = 1
        val bmp = Bitmap.createBitmap(w, h, conf)

        val categoria_evento = binding.autoCompleteCategories.text.toString().trim()
        val userId = uid




        if(titolo_evento.isEmpty()){
            Toast.makeText(this.context, "pppppp", Toast.LENGTH_LONG).show()
        }

        val model= Evento(idEvento, titolo_evento, descrizione_evento, lingue_evento,
            categoria_evento, citta_evento, indirizzo_evento, data_evento, costo_evento,
            npersone_evento, foto_evento, userId)
        viewModelEvento.saveEvent(model)



    }
}
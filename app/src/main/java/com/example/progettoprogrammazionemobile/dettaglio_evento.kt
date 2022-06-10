package com.example.progettoprogrammazionemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.progettoprogrammazionemobile.databinding.FragmentDettaglioEventoBinding
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding


class dettaglio_evento : Fragment() {

    val args: dettaglio_eventoArgs by navArgs()

    private var _binding : FragmentDettaglioEventoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDettaglioEventoBinding.inflate(inflater, container, false)
        val title_click = args.titoloEventoPass
        binding.titoloEventoDett.setText(title_click)

        return binding.root
        //return inflater.inflate(R.layout.fragment_dettaglio_evento, container, false)
    }


}
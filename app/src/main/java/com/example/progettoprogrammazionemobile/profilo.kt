package com.example.progettoprogrammazionemobile

import android.content.Intent
import androidx.fragment.app.FragmentManager

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding
import com.example.progettoprogrammazionemobile.databinding.FragmentProfiloBinding


class profilo : Fragment()  {



    private var _binding : FragmentProfiloBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(


        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        _binding = FragmentProfiloBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button_modifica_prof = binding.modificaprofiloButt
        button_modifica_prof.setOnClickListener{
            view.findNavController().navigate(R.id.action_profilo_to_modifica_profiloFragment2)
        }
        val button_occas_accett = binding.occasioniAccettateProf
        button_occas_accett.setOnClickListener{
            view.findNavController().navigate(R.id.action_profilo_to_occasioni_accettate)
        }

        val button_occas_create = binding.occasionicreateProfilo
        button_occas_create.setOnClickListener{
            view.findNavController().navigate(R.id.action_profilo_to_occasioni_create)
        }

    }






}
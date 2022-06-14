package com.example.progettoprogrammazionemobile
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.progettoprogrammazionemobile.databinding.FragmentProfiloBinding
import com.google.firebase.auth.FirebaseAuth


class profilo : Fragment()  {

    private var _binding : FragmentProfiloBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var uid : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString().trim()
        _binding = FragmentProfiloBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button_mod = binding.modificaprofiloButt
        button_mod.setOnClickListener{
            if(isAdded)  fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, modifica_profilo())?.commit()
        }

        val button_occas_accett = binding.occasioniAccettateProf
        button_occas_accett.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, occasioni_accettate())?.commit()
        }
        val button_occas_create = binding.occasionicreateProfilo
        button_occas_create.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.myNavHostFragment, occasioni_create())?.commit()
        }

        // LOGOUT
        binding.btnLogout.setOnClickListener{
            auth.signOut()
            val intent = Intent (getActivity(), Login::class.java)
            getActivity()?.startActivity(intent)
        }
    }
}
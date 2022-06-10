package com.example.progettoprogrammazionemobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding

class homeFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var contesto: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contesto = this.requireContext()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imgs = listOf<Int>(R.drawable.chicco, R.drawable.rico, R.drawable.ilmioamico)

        var categoryimgs = listOf<category>(
            category(  R.drawable.adventurecategory,"afad"),
            category(  R.drawable.artcategory,"afad"),
            category(  R.drawable.concertcategory,"afad"),
            category(  R.drawable.sportcategory,"afad"),
            category(  R.drawable.photocategory,"afad"),
            category(  R.drawable.rolegamecategory,"afad"),
            category(  R.drawable.partycategory,"afad"))



        val recyclerView = binding.categories
        var mimmo = ImageAdapter(categoryimgs)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = mimmo

        mimmo.setOnItemClickListener(object : ImageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(requireContext(), "dfsdfsdfdsfsfsfsdfsfd",Toast.LENGTH_LONG).show()
            }
        })
        var adapter = Adapter(imgs, this.requireContext())
        var page = binding.viewPager
        page.adapter = adapter
    }

}





package com.example.progettoprogrammazionemobile

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettoprogrammazionemobile.databinding.FragmentHomeBinding

class homeFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imgs = listOf<Int>(R.drawable.chicco, R.drawable.rico, R.drawable.ilmioamico)

        var category_imgs = listOf<category>(
            category(0, R.drawable.adventurecategory, "avventura"),
            category(1, R.drawable.artcategory, "arte"),
            category(2, R.drawable.concertcategory, "musica"),
            category(3, R.drawable.sportcategory, "sport"),
            category(4, R.drawable.photocategory, "photo"),
            category(5, R.drawable.rolegamecategory, "rolegame"),
            category(6, R.drawable.partycategory, "party"))

        var adapter = Adapter(imgs, this.requireContext())
        var page = binding.viewPager
        val recyclerView = binding.categories

        recyclerView.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ImageAdapter(this.requireContext(), category_imgs)

        page.adapter = adapter
    }
}





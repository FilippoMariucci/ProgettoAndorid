package com.example.progettoprogrammazionemobile

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.paging.Pager
import androidx.viewpager.widget.PagerAdapter
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
        var adapter = Adapter(imgs, this.requireContext())
        var page = binding.viewPager
        page.adapter = adapter
    }
}





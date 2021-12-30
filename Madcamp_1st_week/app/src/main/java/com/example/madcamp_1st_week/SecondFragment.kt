package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madcamp_1st_week.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private lateinit var feedAdapter: FeedAdapter
    private var _binding:FragmentSecondBinding? = null
    private val binding get() = _binding!!

    val feedImageList = mutableListOf<FeedItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedAdapter = FeedAdapter(this.requireContext())
        for (i: Int in 1..20) {
            feedImageList.add(FeedItem(R.drawable.netherland, "123"))
        }

        feedAdapter.itemList = feedImageList
        binding.feedGalleryView.adapter = feedAdapter

    }
}
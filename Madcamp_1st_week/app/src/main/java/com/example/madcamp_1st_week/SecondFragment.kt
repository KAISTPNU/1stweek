package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcamp_1st_week.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageAdapter: ImageListAdapter

    val imageList = mutableListOf<ImageItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        imageAdapter = ImageListAdapter(this.requireContext())
        for (i: Int in 1..10) {
            imageList.add(ImageItem(R.drawable.test_image, "123"))
        }
        imageAdapter.imageArrayList = imageList
        binding.imageList.adapter = imageAdapter
        binding.imageList.layoutManager = LinearLayoutManager(activity)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
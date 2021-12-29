package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondFragment : Fragment() {
    private lateinit var imageAdapter: ImageListAdapter

    val imageList = mutableListOf<ImageItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageAdapter = ImageListAdapter(this.requireContext())
        for (i: Int in 1..50) {
            imageList.add(ImageItem(R.drawable.test_image, "123"))
        }

        imageAdapter.imageArrayList = imageList
        val imageRecyclerView = view.findViewById<RecyclerView>(R.id.imageList)
        imageRecyclerView.adapter = imageAdapter
        imageRecyclerView.layoutManager = GridLayoutManager(activity, 3)

    }
}
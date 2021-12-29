package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_1st_week.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var nameAdapter:RecyclerAdapter

    val nameList = mutableListOf<FriendItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first, null)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameAdapter = RecyclerAdapter(this.requireContext())
        for (i: Int in 1..2) {
            nameList.add(FriendItem("홍길동", "010-1234-5678", "동에 번쩍 서에 번쩍 나타난"))
        }

        nameAdapter.datas = nameList
        val nameRecyclerView = view.findViewById<RecyclerView>(R.id.infos)
        nameRecyclerView.adapter = nameAdapter
        nameRecyclerView.layoutManager = GridLayoutManager(activity, 3)
    }


}
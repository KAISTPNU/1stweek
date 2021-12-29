package com.example.madcamp_1st_week

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_1st_week.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var nameAdapter:RecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        for (i: Int in 1..5) {
//            Companion.nameList.add(FriendItem("홍길동", "010-1234-5678", "123"))
//        }

        val view = inflater.inflate(R.layout.fragment_first, null)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameAdapter = RecyclerAdapter(this.requireContext())

        nameAdapter.datas = Companion.nameList
        val nameRecyclerView = view.findViewById<RecyclerView>(R.id.infos)
        nameRecyclerView.adapter = nameAdapter
//        nameRecyclerView.layoutManager = GridLayoutManager(activity, 3)
        nameRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {
        val nameList = mutableListOf<FriendItem>()
    }


}
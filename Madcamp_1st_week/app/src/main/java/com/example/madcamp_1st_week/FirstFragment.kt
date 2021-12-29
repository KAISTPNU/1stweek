package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var developerAdapter:DeveloperAdapter
    private lateinit var designerAdapter:DesignerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        for (i: Int in 1..5) {
            Companion.developerList.add(FriendItem("홍길동", "010-1234-5678", "123", "DESIGNER", "PROGRAMMING", "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.designerList.add(FriendItem("고길동", "010-1234-5678", "123", "DESIGNER", "PROGRAMMING", "KAKAO"))
        }

        val view = inflater.inflate(R.layout.fragment_first, null)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        developerAdapter = DeveloperAdapter(this.requireContext())
        designerAdapter = DesignerAdapter(this.requireContext())

        developerAdapter.developers = Companion.developerList
        designerAdapter.designers = Companion.designerList

        val developerView = view.findViewById<RecyclerView>(R.id.developers)
        val designerView = view.findViewById<RecyclerView>(R.id.designers)
        developerView.adapter = developerAdapter
        designerView.adapter = designerAdapter
//        developerView.layoutManager = GridLayoutManager(activity, 3)
        developerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        designerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {
        val developerList = mutableListOf<FriendItem>()
        val designerList = mutableListOf<FriendItem>()
    }


}
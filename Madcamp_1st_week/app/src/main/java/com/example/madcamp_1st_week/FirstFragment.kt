package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import android.widget.Spinner;

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var developerAdapter:DeveloperAdapter
    private lateinit var designerAdapter:DesignerAdapter
    private lateinit var engineerAdapter: EngineerAdapter
    private lateinit var pmAdapter: PMAdapter
    private lateinit var etcAdapter: EtcAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        for (i: Int in 1..5) {
            Companion.developerList.add(FriendItem("홍길동", "010-1234-5678", "testemail123@naver.com", "DESIGNER", "PROGRAMMING", "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.designerList.add(FriendItem("고길동", "010-1234-5678", "jagosipda@kaist.ac.kr", "DESIGNER", "PROGRAMMING", "KAKAO"))
        }


        val view = inflater.inflate(R.layout.fragment_first, null)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        developerAdapter = DeveloperAdapter(this.requireContext())
        designerAdapter = DesignerAdapter(this.requireContext())
        engineerAdapter = EngineerAdapter(this.requireContext())
        pmAdapter = PMAdapter(this.requireContext())
        etcAdapter = EtcAdapter(this.requireContext())

        developerAdapter.developers = Companion.developerList
        designerAdapter.designers = Companion.designerList
        engineerAdapter.engineers = Companion.engineerList
        pmAdapter.pms = Companion.pmList
        etcAdapter.etcs = Companion.etcList

        val developerView = view.findViewById<ViewPager2>(R.id.viewpager)

//        val designerView = view.findViewById<ViewPager2>(R.id.viewpager)
//        designerView.setBackgroundColor(this.requireContext().getResources().getColor(R.color.darknavy))

        developerView.adapter = developerAdapter
//        designerView.adapter = designerAdapter
        developerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        val spinner = view.findViewById<Spinner>(R.id.dropbox)
        val sAdapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.job, android.R.layout.simple_spinner_item)
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = sAdapter


//        developerView.layoutManager = GridLayoutManager(activity, 3)
//        developerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        designerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {
        val developerList = mutableListOf<FriendItem>()
        val designerList = mutableListOf<FriendItem>()
        val engineerList = mutableListOf<FriendItem>()
        val pmList = mutableListOf<FriendItem>()
        val etcList = mutableListOf<FriendItem>()
    }


}
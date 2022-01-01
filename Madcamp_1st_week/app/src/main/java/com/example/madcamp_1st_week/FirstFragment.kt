package com.example.madcamp_1st_week

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager2.widget.ViewPager2
import com.example.madcamp_1st_week.databinding.FragmentFeedDetailBinding
import com.example.madcamp_1st_week.databinding.FragmentFirstBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var jobsAdapter:JobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        for (i: Int in 1..1) {
            Companion.developerList
                .add(FriendItem("홍길동" + i,
                                "010-1234-5678",
                                "testemail123@naver.com",
                                "DEVELOPER",
                                "PROGRAMMING",
                                "SAMSUNG"))
        }
        for (i: Int in 3..5) {
            Companion.developerList
                .add(FriendItem("홍길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DEVELOPER",
                    "PROGRAMMING",
                    "NAVER"))
        }
        for (i: Int in 6..6) {
            Companion.developerList
                .add(FriendItem("홍길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DEVELOPER",
                    "PROGRAMMING",
                    "APPLE"))
        }
        for (i: Int in 7..7) {
            Companion.developerList
                .add(FriendItem("홍길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DEVELOPER",
                    "PROGRAMMING",
                    "FACEBOOK"))
        }
        for (i: Int in 1..1) {
            Companion.developerList
                .add(FriendItem("홍길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DEVELOPER",
                    "PROGRAMMING",
                    "KAKAO"))
        }
        for (i: Int in 1..2) {
            Companion.developerList
                .add(FriendItem("홍길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DEVELOPER",
                    "PROGRAMMING",
                    "HYUNDAI"))
        }
        for (i: Int in 1..2) {
            Companion.designerList
                .add(FriendItem("김삼성" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DEVELOPER",
                    "PROGRAMMING",
                    "SAMSUNG"))
        }
        for (i: Int in 1..5) {
            Companion.designerList
                .add(FriendItem("고길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DESIGNER",
                    "PROGRAMMING",
                    "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.engineerList
                .add(FriendItem("침착맨" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "ENGINEER",
                    "PROGRAMMING",
                    "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.pmList
                .add(FriendItem("기안" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "PM",
                    "PROGRAMMING",
                    "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.etcList
                .add(FriendItem("누구" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "ETC",
                    "PROGRAMMING",
                    "SAMSUNG"))
        }


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobsAdapter = JobsAdapter(this.requireContext())

        jobsAdapter.developers = Companion.developerList
        jobsAdapter.designers = Companion.designerList
        jobsAdapter.engineers = Companion.engineerList
        jobsAdapter.pms = Companion.pmList
        jobsAdapter.etcs = Companion.etcList

//        val spinner = view.findViewById<Spinner>(R.id.dropbox)
        val sAdapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.job, android.R.layout.simple_spinner_item)
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropbox.adapter = sAdapter

        binding.dropbox.setSelection(0)


//        val image = view.findViewById<ImageView>(R.id.devImage)
        viewpagerView = view.findViewById<ViewPager2>(R.id.viewpager)

        binding.dropbox.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    //developer
                    0 -> {
                        binding.devImage.setImageResource(R.drawable.developer)
                        jobsAdapter.job = 0
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                    }
                    //designer
                    1 -> {
                        binding.devImage.setImageResource(R.drawable.designer)
                        jobsAdapter.job = 1
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                    }
                    // engineer
                    2 -> {
                        binding.devImage.setImageResource(R.drawable.engineer)
                        jobsAdapter.job = 2
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                    }
                    //pm
                    3 -> {
                        binding.devImage.setImageResource(R.drawable.pm)
                        jobsAdapter.job = 3
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                    }
                    //etc
                    4 -> {
                        binding.devImage.setImageResource(R.drawable.etc)
                        jobsAdapter.job = 4
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

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
        lateinit var viewpagerView: ViewPager2
    }


}
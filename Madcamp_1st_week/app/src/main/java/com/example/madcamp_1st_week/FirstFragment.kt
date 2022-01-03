package com.example.madcamp_1st_week

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.madcamp_1st_week.databinding.FragmentFirstBinding
import org.json.JSONArray
import org.json.JSONObject


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var jobsAdapter:JobAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        initSampleData()
        readData()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*
            스마트폰 상태바의 색상을 바꿔서 마치 전체화면처럼 보이게 합니다
         */
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.lightgray)

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobsAdapter = JobAdapter(this.requireContext())

        jobsAdapter.developers = Companion.developerList
        jobsAdapter.designers = Companion.designerList
        jobsAdapter.engineers = Companion.engineerList
        jobsAdapter.pms = Companion.pmList
        jobsAdapter.etcs = Companion.etcList

        val sAdapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.job, android.R.layout.simple_spinner_item)
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropbox.adapter = sAdapter

        binding.dropbox.setSelection(0)

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
                        binding.dotsIndicator.setViewPager2(viewpagerView)
                    }
                    //designer
                    1 -> {
                        binding.devImage.setImageResource(R.drawable.designer)
                        jobsAdapter.job = 1
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                        binding.dotsIndicator.setViewPager2(viewpagerView)
                    }
                    // engineer
                    2 -> {
                        binding.devImage.setImageResource(R.drawable.engineer)
                        jobsAdapter.job = 2
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                        binding.dotsIndicator.setViewPager2(viewpagerView)
                    }
                    //pm
                    3 -> {
                        binding.devImage.setImageResource(R.drawable.pm)
                        jobsAdapter.job = 3
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                        binding.dotsIndicator.setViewPager2(viewpagerView)
                    }
                    //etc
                    4 -> {
                        binding.devImage.setImageResource(R.drawable.etc)
                        jobsAdapter.job = 4
                        viewpagerView.adapter=jobsAdapter
                        viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
                        binding.dotsIndicator.setViewPager2(viewpagerView)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    fun readData() {
        developerList.clear()
        designerList.clear()
        engineerList.clear()
        pmList.clear()
        etcList.clear()

        val jsonString = activity?.assets?.open("profileList.json")?.reader()?.readText()
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val name = jsonObject.getString("name")
            val email = jsonObject.getString("email")
            val phone = jsonObject.getString("phone")
            val job = jsonObject.getString("job")
            val detailjob = jsonObject.getString("detailjob")
            val company = jsonObject.getString("company")

            when(job.uppercase()) {
                "DEVELOPER" -> developerList.add(JobItem(name, email, phone, job, detailjob, company))
                "DESIGNER" -> designerList.add(JobItem(name, email, phone, job, detailjob, company))
                "ENGINEER" -> engineerList.add(JobItem(name, email, phone, job, detailjob, company))
                "PM" -> pmList.add(JobItem(name, email, phone, job, detailjob, company))
                "ETC" -> etcList.add(JobItem(name, email, phone, job, detailjob, company))
            }
        }
    }

    fun initSampleData() {
        developerList.clear()
        designerList.clear()
        engineerList.clear()
        pmList.clear()
        etcList.clear()

        for (i: Int in 1..5) {
            Companion.developerList
                .add(JobItem("홍길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DEVELOPER",
                    "PROGRAMMING",
                    "SAMSUNG"))
        }

        for (i: Int in 1..5) {
            Companion.designerList
                .add(JobItem("고길동" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "DESIGNER",
                    "PROGRAMMING",
                    "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.engineerList
                .add(JobItem("침착맨" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "ENGINEER",
                    "PROGRAMMING",
                    "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.pmList
                .add(JobItem("기안" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "PM",
                    "PROGRAMMING",
                    "KAKAO"))
        }
        for (i: Int in 1..5) {
            Companion.etcList
                .add(JobItem("누구" + i,
                    "010-1234-5678",
                    "testemail123@naver.com",
                    "ETC",
                    "PROGRAMMING",
                    "SAMSUNG"))
        }
    }

    companion object {
        val developerList = mutableListOf<JobItem>()
        val designerList = mutableListOf<JobItem>()
        val engineerList = mutableListOf<JobItem>()
        val pmList = mutableListOf<JobItem>()
        val etcList = mutableListOf<JobItem>()
        lateinit var viewpagerView: ViewPager2
    }
}

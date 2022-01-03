package com.example.madcamp_1st_week

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.madcamp_1st_week.databinding.FragmentFirstBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var jobAdapterList = mutableListOf<ProfileAdapter>()
    private var devImageResourceList = mutableListOf<Int>(R.drawable.developer, R.drawable.designer, R.drawable.engineer, R.drawable.pm, R.drawable.etc)
    private var fileNameList = mutableListOf<String>("profileList.json", "profileList.json", "profileList.json", "profileList.json", "profileList.json")

//    private lateinit var jobsAdapter:JobAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initJobAdapterList()
    }

    fun initJobAdapterList() {
        jobAdapterList.clear()
        for (i in 0..4) {
            jobAdapterList.add(ProfileAdapter(this.requireContext(), fileNameList[i]))
//            jobAdapterList[i].readJsonData()
        }
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
        firstFragmentBinding = _binding as @NonNull FragmentFirstBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sAdapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.job, android.R.layout.simple_spinner_item)
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropbox.adapter = sAdapter

        binding.dropbox.setSelection(0)

        viewpagerView = binding.viewpager
        dotsIncicator = binding.dotsIndicator

        binding.viewpager.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.adapter = jobAdapterList[0]
        binding.dotsIndicator.setViewPager(binding.viewpager)

        binding.dropbox.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.viewpager.adapter = jobAdapterList[position]
                binding.devImage.setImageResource(devImageResourceList[position])
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    companion object {
        val developerList = mutableListOf<ProfileItem>()
        val designerList = mutableListOf<ProfileItem>()
        val engineerList = mutableListOf<ProfileItem>()
        val pmList = mutableListOf<ProfileItem>()
        val etcList = mutableListOf<ProfileItem>()
        lateinit var viewpagerView: ViewPager2
        lateinit var dotsIncicator: CircleIndicator3
        lateinit var firstFragmentBinding: FragmentFirstBinding
    }
}

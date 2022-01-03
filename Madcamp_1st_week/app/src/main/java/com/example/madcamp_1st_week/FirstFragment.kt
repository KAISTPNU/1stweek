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
import me.relex.circleindicator.CircleIndicator3
import org.json.JSONObject


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var profileAdapterList = mutableListOf<ProfileAdapter>()
    private var devImageResourceList = mutableListOf<Int>(R.drawable.developer, R.drawable.designer, R.drawable.engineer, R.drawable.pm, R.drawable.etc)
    private var fileNameList = mutableListOf<String>("developer.json", "designer.json", "engineer.json", "pm.json", "etc.json")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initJobAdapterList()
    }

    fun initJobAdapterList() {
        profileAdapterList.clear()
        for (i in 0..4) {
            profileAdapterList.add(ProfileAdapter(this.requireContext(), fileNameList[i]))
            profileAdapterList[i].readJsonData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.lightgray)
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        firstFragmentBinding = _binding as @NonNull FragmentFirstBinding

        val args = arguments
        if (args != null) {
            var jsonString = args.getString("profile")
            var jsonObject = JSONObject(jsonString)
            var name = jsonObject.getString("name")
            var email = jsonObject.getString("email")
            var phone = jsonObject.getString("phone")
            var job = jsonObject.getString("job")
            var detailjob = jsonObject.getString("detailjob")
            var company = jsonObject.getString("company")

            var position = when(job.uppercase()) {
                "DEVELOPER"-> 0
                "DESIGNER"-> 1
                "ENGINEER"-> 2
                "PM"-> 3
                else -> 4
            }

            profileAdapterList[position].itemList.add(ProfileItem(name, email, phone, job, detailjob, company))
            profileAdapterList[position].writeJsonData()
        }

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

        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.adapter = profileAdapterList[0]
        binding.dotsIndicator.setViewPager(binding.viewpager)

        binding.dropbox.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.viewpager.adapter = profileAdapterList[position]
                binding.devImage.setImageResource(devImageResourceList[position])
                binding.dotsIndicator.setViewPager(binding.viewpager)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    companion object {
        lateinit var viewpagerView: ViewPager2
        lateinit var dotsIncicator: CircleIndicator3
        lateinit var firstFragmentBinding: FragmentFirstBinding
    }
}

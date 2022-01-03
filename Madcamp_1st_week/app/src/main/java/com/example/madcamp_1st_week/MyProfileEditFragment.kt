package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.madcamp_1st_week.databinding.FragmentAddContactBinding
import com.example.madcamp_1st_week.databinding.FragmentMyProfileEditBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

class MyProfileEditFragment : Fragment() {
    private var _binding:FragmentMyProfileEditBinding? = null
    private val binding get() = _binding!!

    private var name:String? = null
    private var email:String? = null
    private var phone:String? = null
    private var job:String? = null
    private var detailjob:String? = null
    private var company:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readJsonData()
        initEditButton()
        binding.nameInput.setText(name)
        binding.emailInput.setText(email)
        binding.phoneInput.setText(phone)
        binding.jobInput.setText(job)
        binding.detailJobInput.setText(detailjob)
        binding.companyInput.setText(company)
    }

    fun initEditButton() {
        binding.profileEditBtn.setOnClickListener{
            name = binding.nameInput.text.toString()
            email = binding.emailInput.text.toString()
            phone = binding.phoneInput.text.toString()
            job = binding.jobInput.text.toString()
            detailjob = binding.detailJobInput.text.toString()
            company = binding.companyInput.text.toString()
            writeJsonData()

            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                ?.replace(R.id.fragment, MyProfileFragment())
                ?.commitAllowingStateLoss()

//            Toast.makeText(activity,"[" + name + "] is added!", Toast.LENGTH_SHORT).show()
        }
    }

    fun writeJsonData() {
        var jsonFile = File(requireActivity().filesDir, "myProfile.json")
        var writer = BufferedWriter(FileWriter(jsonFile))
        var jsonObject = JSONObject()

        jsonObject.put("name", name)
        jsonObject.put("email", email)
        jsonObject.put("phone", phone)
        jsonObject.put("job", job)
        jsonObject.put("detailjob", detailjob)
        jsonObject.put("company", company)

        writer.write(jsonObject.toString())
        writer.close()
    }

    private fun readJsonData() {
        var jsonFile = File(requireActivity().filesDir, "myProfile.json")
        when (jsonFile.exists()) {
            true -> {

                var reader = FileReader(jsonFile)
                var jsonString = BufferedReader(reader).readText()

                if (!jsonString.isNullOrBlank()) {
                    var jsonObject = JSONObject(jsonString)

                    name = jsonObject.getString("name")
                    email = jsonObject.getString("email")
                    phone = jsonObject.getString("phone")
                    job = jsonObject.getString("job")
                    detailjob = jsonObject.getString("detailjob")
                    company = jsonObject.getString("company")
                }
            }
            false -> File(requireActivity().filesDir, "myProfile.json").createNewFile()
        }
    }
}
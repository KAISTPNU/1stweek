package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.madcamp_1st_week.databinding.FragmentAddContactBinding
import org.json.JSONObject

class ContactAddFragment : Fragment() {
    private var _binding:FragmentAddContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddButton()

        val args = arguments
        var jsonObj = JSONObject(args?.getString("JSON")!!)
        var name = jsonObj.getString("name")
        var email = jsonObj.getString("email")
        var phone = jsonObj.getString("phone")
        var job = jsonObj.getString("job")
        var detailjob = jsonObj.getString("detailjob")
        var company = jsonObj.getString("company")

        binding.nameInput.setText(name)
        binding.emailInput.setText(email)
        binding.phoneInput.setText(phone)
        binding.jobInput.setText(job)
        binding.detailJobInput.setText(detailjob)
        binding.companyInput.setText(company)
    }

    fun initAddButton() {
        binding.contactAddBtn.setOnClickListener{
            var name = binding.nameInput.text.toString()
            var email = binding.emailInput.text.toString()
            var phone = binding.phoneInput.text.toString()
            var job = binding.jobInput.text.toString()
            var detailjob = binding.detailJobInput.text.toString()
            var company = binding.companyInput.text.toString()

            when(job.uppercase()) {
                "DEVELOPER" -> FirstFragment.developerList
                    .add(ProfileItem(name, phone, email, job, detailjob, company))

                "DESIGNER" -> FirstFragment.designerList
                    .add(ProfileItem(name, phone, email, job, detailjob, company))

                "ENGINEER" -> FirstFragment.engineerList
                    .add(ProfileItem(name, phone, email, job, detailjob, company))

                "PM" -> FirstFragment.pmList
                    .add(ProfileItem(name, phone, email, job, detailjob, company))

                else -> FirstFragment.etcList
                        .add(ProfileItem(name, phone, email, job, detailjob, company))
            }

//            if (job.uppercase().equals("DEVELOPER")) {
//                FirstFragment.developerList.add(JobItem(name, phone, email, job, detailjob, company))
//            }
//            else if (job.uppercase().equals("DESIGNER")) {
//                FirstFragment.designerList.add(JobItem(name, phone, email, job, detailjob, company))
//            }
//            else if (job.uppercase().equals("ENGINEER")) {
//                FirstFragment.engineerList.add(JobItem(name, phone, email, job, detailjob, company))
//            }
//            else if (job.uppercase().equals("PM")) {
//                FirstFragment.pmList.add(JobItem(name, phone, email, job, detailjob, company))
//            }
//            else {
//                FirstFragment.etcList.add(JobItem(name, phone, email, job, detailjob, company))
//            }

            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment, FirstFragment())
                ?.disallowAddToBackStack()
                ?.commitAllowingStateLoss()

            Toast.makeText(activity,"[" + name + "] is added!", Toast.LENGTH_SHORT).show()
        }
    }

}
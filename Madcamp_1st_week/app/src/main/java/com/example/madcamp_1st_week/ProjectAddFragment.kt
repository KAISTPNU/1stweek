package com.example.madcamp_1st_week

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.madcamp_1st_week.databinding.FragmentAddContactBinding
import com.example.madcamp_1st_week.databinding.FragmentAddProjectBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ProjectAddFragment : Fragment() {
    private var _binding:FragmentAddProjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startDateInput.setOnClickListener {

            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var date_listener = object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    var startDate = "${year} - ${month+1} - ${dayOfMonth}"
                    binding.startDateInput.setText(startDate)
                }
            }
            var builder = DatePickerDialog(this.requireContext(), date_listener, year, month, day)
            builder.show()
        }

        binding.endDateInput.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var date_listener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    var endDate = "${year} - ${month + 1} - ${dayOfMonth}"
                    binding.endDateInput.setText(endDate)
                }

            }
            var builder = DatePickerDialog(this.requireContext(), date_listener, year, month, day)
            builder.show()
        }
        initAddButton()

    }

    fun initAddButton() {
        binding.contactAddBtn.setOnClickListener{
            var name = binding.nameInput.text.toString()
            var email = binding.emailInput.text.toString()
            var phone = binding.phoneInput.text.toString()
            var projectTitle = binding.titleInput.toString()
            var language = binding.languageInput.toString()
            var startDate = binding.startDateInput.text.toString()
            var endDate = binding.endDateInput.text.toString()

            var bundle: Bundle = Bundle()
            var jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("email", email)
            jsonObject.put("phone", phone)
            jsonObject.put("title", projectTitle)
            jsonObject.put("language", language)
            jsonObject.put("startdate", startDate)
            jsonObject.put("startdate", endDate)

            bundle.putString("profile", jsonObject.toString())
            var firstFragment = FirstFragment()
            firstFragment.arguments = bundle

            val dialog = ProfileAddDialog(this.requireContext())
            dialog.setOnOKClickedListener{
                activity?.supportFragmentManager?.beginTransaction()
                    ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    ?.replace(R.id.fragment, firstFragment)
                    ?.commitAllowingStateLoss()
            }
            dialog.start("dsf")
        }
    }

}
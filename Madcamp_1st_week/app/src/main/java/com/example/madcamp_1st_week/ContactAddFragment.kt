package com.example.madcamp_1st_week

import android.app.AlertDialog
import android.content.DialogInterface
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

            var bundle: Bundle = Bundle()
            var jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("email", email)
            jsonObject.put("phone", phone)
            jsonObject.put("job", job)
            jsonObject.put("detailjob", detailjob)
            jsonObject.put("company", company)

            bundle.putString("profile", jsonObject.toString())
            var firstFragment = FirstFragment()
            firstFragment.arguments = bundle

            val popup = AlertDialog.Builder(context)
            popup
//                .setMessage("Do you want to delete it?")
                .setView(R.layout.dialog_item)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    //Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
                })
            popup.show()

            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                ?.replace(R.id.fragment, firstFragment)
                ?.commitAllowingStateLoss()

            Toast.makeText(activity,"[" + name + "] is added!", Toast.LENGTH_SHORT).show()
        }
    }

}
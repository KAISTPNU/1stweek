package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        binding.nameInput.setText(name)
        binding.emailInput.setText(email)
        binding.phoneInput.setText(phone)
    }

    fun initAddButton() {
        binding.contactAddBtn.setOnClickListener{
            var name = binding.nameInput.text.toString()
            var email = binding.emailInput.text.toString()
            var phone = binding.phoneInput.text.toString()

            FirstFragment.nameList.add(FriendItem(name, phone, email))

            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment, FirstFragment())
                ?.disallowAddToBackStack()
                ?.commitAllowingStateLoss()

            Toast.makeText(activity,"[" + name + "] is added!", Toast.LENGTH_SHORT).show()
        }
    }

}
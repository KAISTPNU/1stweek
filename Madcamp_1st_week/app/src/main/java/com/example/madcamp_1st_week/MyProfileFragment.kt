package com.example.madcamp_1st_week

import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madcamp_1st_week.databinding.FragmentMyProfileBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONArray
import org.json.JSONObject

class MyProfileFragment : Fragment() {
    private var _binding:FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    private var jsonString:String? = null
    private var name:String? = null
    private var email:String? = null
    private var phone:String? = null
    private var job:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)

        /*
            스마트폰 상태바의 색상을 바꿔서 마치 전체화면처럼 보이게 합니다
         */
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.lightgray)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeCardViewBorderToBlack()
        readJsonData()
        generateQRCode()
        binding.myProfileItem.name.text = name
        binding.myProfileItem.email.text = email
        binding.myProfileItem.phone.text = phone
        binding.myProfileItem.company.text = job
    }

    private fun changeCardViewBorderToBlack() {
        binding.myProfileItem.profileCardBorder
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
    }
    private fun readJsonData() {
        jsonString = activity?.assets?.open("myProfile.json")?.reader()?.readText()

        val jsonObject = JSONObject(jsonString)
        name = jsonObject.getString("name")
        email = jsonObject.getString("email")
        phone = jsonObject.getString("phone")
        job = jsonObject.getString("job")
    }

    private fun generateQRCode() {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(jsonString, BarcodeFormat.QR_CODE, 512, 512)
        binding.myProfileItem.profile.setImageBitmap(bitmap)
        binding.myProfileItem.profileCardView.radius = 5f
    }
}
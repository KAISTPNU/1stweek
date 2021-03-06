package com.example.madcamp_1st_week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.madcamp_1st_week.databinding.FragmentMyProfileBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class MyProfileFragment : Fragment() {
    private var _binding:FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    private var jsonString:String? = null
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
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)

        /*
            스마트폰 상태바의 색상을 바꿔서 마치 전체화면처럼 보이게 합니다
         */
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.lightgray)

        binding.myProfileItem.delete.setImageResource(R.drawable.baseline_settings)
        binding.myProfileItem.delete.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeCardViewBorderToBlack()
        readJsonData()

        binding.myProfileItem.name.text = name
        binding.myProfileItem.email.text = email
        binding.myProfileItem.phone.text = phone
        binding.myProfileItem.company.text = job
        binding.myProfileItem.detailjob.text = detailjob
        binding.myProfileItem.company.text = company

        binding.myProfileItem.profile.imageTintList = null

        binding.myProfileItem.delete.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                ?.replace(R.id.fragment, MyProfileEditFragment())
                ?.commitAllowingStateLoss()
        }
    }

    private fun changeCardViewBorderToBlack() {
        binding.myProfileItem.profileCardBorder
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun readJsonData() {
        var jsonFile = File(requireActivity().filesDir, "myProfile.json")
        when (jsonFile.exists()) {
            true -> {

                var reader = FileReader(jsonFile)

                jsonString = BufferedReader(reader).readText()
                if (!jsonString.isNullOrBlank()) {
                    var jsonObject = JSONObject(jsonString)

                    name = jsonObject.getString("name")
                    email = jsonObject.getString("email")
                    phone = jsonObject.getString("phone")
                    job = jsonObject.getString("job")
                    detailjob = jsonObject.getString("detailjob")
                    company = jsonObject.getString("company")

                    generateQRCode()
                }
                else {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        ?.replace(R.id.fragment, MyProfileEditFragment())
                        ?.commitAllowingStateLoss()
                }
            }
            false -> {
                File(requireActivity().filesDir, "myProfile.json").createNewFile()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    ?.replace(R.id.fragment, MyProfileEditFragment())
                    ?.commitAllowingStateLoss()
            }
        }
    }

    private fun generateQRCode() {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(jsonString, BarcodeFormat.QR_CODE, 512, 512)
        binding.myProfileItem.profile.setImageBitmap(bitmap)
        binding.myProfileItem.profileCardView.radius = 10f
    }
}
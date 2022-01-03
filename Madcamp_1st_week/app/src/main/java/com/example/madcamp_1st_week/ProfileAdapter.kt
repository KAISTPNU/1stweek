package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import com.example.madcamp_1st_week.databinding.FragmentFirstBinding
import org.json.JSONArray
import java.io.*
import java.lang.reflect.InvocationTargetException


class ProfileAdapter (private val context: Context, private val fileName: String): RecyclerView.Adapter<ProfileAdapter.ViewHolder>(){
    private lateinit var viewPagerBinding: FragmentFirstBinding

    var itemList = mutableListOf<ProfileItem>()
    private lateinit var jsonArray: JSONArray
    private lateinit var deleteBtn : ImageButton

    init { readJsonData() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        viewPagerBinding = FragmentFirstBinding.inflate(LayoutInflater.from(context))

        val view = LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false)
        deleteBtn = view.findViewById<ImageButton>(R.id.delete)
        var viewholder = ViewHolder(view)
        deleteBtn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val popup = AlertDialog.Builder(context)
                popup
                    .setMessage("Do you want to delete it?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                        delete(viewholder)
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        //Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
                    })
                popup.show()
            }
        })
        return viewholder
    }

    fun delete(viewholder: ViewHolder) { viewholder.unbind(FirstFragment.firstFragmentBinding.viewpager.currentItem) }

    override fun getItemCount(): Int { return itemList.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bind(itemList[position]) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPhone: TextView = itemView.findViewById(R.id.phone)
        private val txtEmail: TextView = itemView.findViewById(R.id.email)
        private val txtDetailJob: TextView = itemView.findViewById(R.id.detailjob)
        private val txtCompany: TextView = itemView.findViewById(R.id.company)

        @SuppressLint("ResourceAsColor")
        fun bind(item: ProfileItem) {
            txtName.text = item.name
            txtPhone.text = item.phone
            txtEmail.text = item.email
            txtDetailJob.text = item.detailjob
            txtCompany.text = item.company

            val profileCardBorder = itemView.findViewById<LinearLayout>(R.id.profile_card_border)
            val deleteBtn = itemView.findViewById<ImageButton>(R.id.delete)

            when(item.company.uppercase()) {
                "SAMSUNG" -> setColorOfProfileCardBorderAndDeleteButton(profileCardBorder, deleteBtn, R.color.samsung)
                "KAKAO" -> setColorOfProfileCardBorderAndDeleteButton(profileCardBorder, deleteBtn, R.color.kakao)
                "NAVER" -> setColorOfProfileCardBorderAndDeleteButton(profileCardBorder, deleteBtn, R.color.naver)
                "FACEBOOK" -> setColorOfProfileCardBorderAndDeleteButton(profileCardBorder, deleteBtn, R.color.facebook)
                "APPLE" -> setColorOfProfileCardBorderAndDeleteButton(profileCardBorder, deleteBtn, R.color.apple)
                else -> setColorOfProfileCardBorderAndDeleteButton(profileCardBorder, deleteBtn, R.color.darknavy)
            }
        }

        fun setColorOfProfileCardBorderAndDeleteButton(cardBorder: LinearLayout, deleteBtn: ImageButton, color: Int) {
            cardBorder.setBackgroundColor(ContextCompat.getColor(context, color))
            deleteBtn.setColorFilter(ContextCompat.getColor(context, color))
        }

        fun unbind(position: Int) {
            itemList.removeAt(position)
            jsonArray.remove(position)

            FirstFragment.firstFragmentBinding.viewpager.adapter = this@ProfileAdapter
            if (position > itemList.size) { FirstFragment.firstFragmentBinding.viewpager.currentItem = itemList.size }
            else {  FirstFragment.firstFragmentBinding.viewpager.currentItem = position }
            writeJsonData()
            notifyDataSetChanged()
            FirstFragment.firstFragmentBinding.dotsIndicator.setViewPager(FirstFragment.firstFragmentBinding.viewpager)
        }
    }

    fun writeJsonData() {
        var jsonFile = File(context.filesDir, fileName)
        var writer = BufferedWriter(FileWriter(jsonFile))
        writer.write(jsonArray.toString())
        writer.close()
    }

    fun readJsonData() {
        var jsonFile = File(context.filesDir, fileName)
        when (jsonFile.exists()) {
            true -> {
                var reader = FileReader(jsonFile)
                var jsonString = BufferedReader(reader).readText()
                jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val name = jsonObject.getString("name")
                    val email = jsonObject.getString("email")
                    val phone = jsonObject.getString("phone")
                    val job = jsonObject.getString("job")
                    val detailjob = jsonObject.getString("detailjob")
                    val company = jsonObject.getString("company")
                    itemList.add(ProfileItem(name, email, phone, job, detailjob, company))
                }
            }
            false -> File(context.filesDir, fileName).createNewFile()
        }
//        var jsonString = BufferedReader(FileReader(File(context.filesDir, fileName))).readText()
//        jsonArray = JSONArray(jsonString)
//        for (i in 0 until jsonArray.length()) {
//            val jsonObject = jsonArray.getJSONObject(i)
//            val name = jsonObject.getString("name")
//            val email = jsonObject.getString("email")
//            val phone = jsonObject.getString("phone")
//            val job = jsonObject.getString("job")
//            val detailjob = jsonObject.getString("detailjob")
//            val company = jsonObject.getString("company")
//            itemList.add(ProfileItem(name, email, phone, job, detailjob, company))
//        }
    }
}
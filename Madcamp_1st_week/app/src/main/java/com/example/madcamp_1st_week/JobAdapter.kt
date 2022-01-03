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
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray


class JobAdapter (private val context: Context, private val fileName: String): RecyclerView.Adapter<JobAdapter.ViewHolder>(){

//    var designers = mutableListOf<JobItem>()
//    var developers = mutableListOf<JobItem>()
//    var engineers = mutableListOf<JobItem>()
//    var pms = mutableListOf<JobItem>()
//    var etcs = mutableListOf<JobItem>()

    var itemList = mutableListOf<JobItem>()
    lateinit var jsonArray: JSONArray

//    var job: Int = 0
    private lateinit var deleteBtn : ImageButton

    fun readJsonData() {
        val jsonString = context?.assets?.open("profileList.json")?.reader()?.readText()
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val name = jsonObject.getString("name")
            val email = jsonObject.getString("email")
            val phone = jsonObject.getString("phone")
            val job = jsonObject.getString("job")
            val detailjob = jsonObject.getString("detailjob")
            val company = jsonObject.getString("company")
            itemList.add(JobItem(name, email, phone, job, detailjob, company))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobAdapter.ViewHolder {
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

    fun delete(viewholder: ViewHolder) {
        val layout = FirstFragment.viewpagerView
        var viewpagerView = layout.findViewById<ViewPager2>(R.id.viewpager)
        var index = viewpagerView.currentItem

        viewholder.unbind(index)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
//        var count: Int = 0
//        when(job) {
//            0 -> count = developers.size
//            1 -> count = designers.size
//            2 -> count = engineers.size
//            3 -> count = pms.size
//            4 -> count = etcs.size
//        }
//        return count
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        when(job) {
//            0 -> holder.bind(developers[position])
//            1 -> holder.bind(designers[position])
//            2 -> holder.bind(engineers[position])
//            3 -> holder.bind(pms[position])
//            4 -> holder.bind(etcs[position])
//        }
        holder.bind(itemList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPhone: TextView = itemView.findViewById(R.id.phone)
        private val txtEmail: TextView = itemView.findViewById(R.id.email)
        private val txtDetailJob: TextView = itemView.findViewById(R.id.detailjob)
        private val txtCompany: TextView = itemView.findViewById(R.id.company)

        @SuppressLint("ResourceAsColor")
        fun bind(item: JobItem) {
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
//            when(job) {
//                0 -> developers.removeAt(position)
//                1 -> designers.removeAt(position)
//                2 -> engineers.removeAt(position)
//                3 -> pms.removeAt(position)
//                4 -> etcs.removeAt(position)
//            }
            itemList.removeAt(position)
        }

    }
}
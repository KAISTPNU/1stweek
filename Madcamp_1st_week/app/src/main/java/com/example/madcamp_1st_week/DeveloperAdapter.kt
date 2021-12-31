package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext


class DeveloperAdapter (private val context: Context): RecyclerView.Adapter<DeveloperAdapter.PagerViewHolder>(){

    var developers = mutableListOf<FriendItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperAdapter.PagerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent,false)
        return PagerViewHolder(view)
    }

    override fun getItemCount(): Int = developers.size
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(developers[position])
    }

    inner class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPhone: TextView = itemView.findViewById(R.id.phone)
        private val txtEmail: TextView = itemView.findViewById(R.id.email)
        private val txtCompany: TextView = itemView.findViewById(R.id.company)
        private val txtDetailJob: TextView = itemView.findViewById(R.id.detailjob)

        @SuppressLint("ResourceAsColor")
        fun bind(item: FriendItem) {
            txtName.text = item.name
            txtPhone.text = item.phone
            txtEmail.text = item.email
            txtDetailJob.text = item.detailjob
            txtCompany.text = item.company

            var profileCardBorder = itemView.findViewById<LinearLayout>(R.id.profile_card_border)
            when(item.company) {
                "SAMSUNG" -> profileCardBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.samsung))
                "KAKAO" -> profileCardBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.kakao))
                else -> profileCardBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.darknavy))
            }
        }
    }
}
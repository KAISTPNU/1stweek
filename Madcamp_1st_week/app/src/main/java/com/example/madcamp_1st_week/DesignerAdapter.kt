package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView



class DesignerAdapter (private val context: Context): RecyclerView.Adapter<DesignerAdapter.ViewHolder>(){

    var designers = mutableListOf<FriendItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignerAdapter.ViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,false)
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = designers.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(designers[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPhone: TextView = itemView.findViewById(R.id.phone)
        private val txtEmail: TextView = itemView.findViewById(R.id.email)
        private val txtDetailJob: TextView = itemView.findViewById(R.id.detailjob)
        private val txtCompany: TextView = itemView.findViewById(R.id.company)

        @SuppressLint("ResourceAsColor")
        fun bind(item: FriendItem) {
            txtName.text = item.name
            txtPhone.text = item.phone
            txtEmail.text = item.email
            txtDetailJob.text = item.detailjob
            txtCompany.text = item.company

            var profileCardBorder = itemView.findViewById<LinearLayout>(R.id.profile_card_border)
            when(item.company.toString()) {
                "SAMSUNG" -> profileCardBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.samsung))
                "KAKAO" -> profileCardBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.kakao))
                else -> profileCardBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.darknavy))
            }
        }

    }
}
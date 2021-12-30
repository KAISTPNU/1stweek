package com.example.madcamp_1st_week

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EngineerAdapter (private val context: Context): RecyclerView.Adapter<EngineerAdapter.PagerViewHolder>(){

    var engineers = mutableListOf<FriendItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerAdapter.PagerViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,false)
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false)
        return PagerViewHolder(view)
    }

    override fun getItemCount(): Int = engineers.size
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(engineers[position])
    }

    inner class PagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPhone: TextView = itemView.findViewById(R.id.phone)
        private val txtEmail: TextView = itemView.findViewById(R.id.email)
        private val txtCompany: TextView = itemView.findViewById(R.id.company)
        private val txtDetailJob: TextView = itemView.findViewById(R.id.detailjob)

        fun bind(item: FriendItem) {
            txtName.text = item.name
            txtPhone.text = item.phone
            txtEmail.text = item.email
            txtDetailJob.text = item.detailjob
            txtCompany.text = item.company

        }

    }
}
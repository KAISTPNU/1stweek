package com.example.madcamp_1st_week

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class RecyclerAdapter (private val context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    var datas = mutableListOf<FriendItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPhone: TextView = itemView.findViewById(R.id.phone)
        private val txtEmail: TextView = itemView.findViewById(R.id.email)

        fun bind(item: FriendItem) {
            txtName.text = item.name
            txtPhone.text = item.phone
            txtEmail.text = item.email

        }

    }
}
package com.example.madcamp_1st_week

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageListAdapter(private val context: Context):
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

        private var imageArrayList = mutableListOf<ImageItem>()
        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            private val imageView: ImageView = itemView.findViewById(R.id.imageItem)
            private val description: TextView = itemView.findViewById(R.id.description)


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}

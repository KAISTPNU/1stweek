package com.example.madcamp_1st_week

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageListAdapter(private val context: Context):
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    var imageArrayList = mutableListOf<ImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageArrayList[position])
    }

    override fun getItemCount(): Int {
        return imageArrayList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageItem)
        private val descriptionView: TextView = itemView.findViewById(R.id.description)

        fun bind(imageItem: ImageItem) {
            imageView.setImageResource(imageItem.resourceID)
            descriptionView.setText(imageItem.description)
        }

    }
}

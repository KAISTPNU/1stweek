package com.example.madcamp_1st_week

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.fragment.app.findFragment

class FeedAdapter(private val context: Context):
    BaseAdapter() {

    var itemList = mutableListOf<FeedItem>()

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(p0: Int): Any {
        return itemList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val feedItem: FeedItem = itemList[p0]
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val feedView: View = inflater.inflate(R.layout.feed_gallery_item, null)
        val feedGalleryImg = feedView.findViewById<ImageView>(R.id.feed_gallery_img)
        feedGalleryImg.setImageResource(feedItem.resourceID)
        return feedView
    }
}

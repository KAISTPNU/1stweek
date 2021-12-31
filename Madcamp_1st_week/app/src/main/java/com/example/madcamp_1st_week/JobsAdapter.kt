package com.example.madcamp_1st_week

import android.annotation.SuppressLint
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



class JobsAdapter (private val context: Context): RecyclerView.Adapter<JobsAdapter.ViewHolder>(){

    var designers = mutableListOf<FriendItem>()
    var developers = mutableListOf<FriendItem>()
    var engineers = mutableListOf<FriendItem>()
    var pms = mutableListOf<FriendItem>()
    var etcs = mutableListOf<FriendItem>()
    var job: Int = 0

    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT)
    }
    val negativeButtonClick = { dialogInterface: DialogInterface, i: Int ->
        Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false)
        var deleteButton = view.findViewById<ImageButton>(R.id.delete)

        var jobsAdapter = JobsAdapter(context)
        var viewholder = ViewHolder(view)
        deleteButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
//                val popup = AlertDialog.Builder(context)
//                    .setTitle("Test")
//                    .setMessage("Do you want to delete it?")
//                    .setPositiveButton("Yes", positiveButtonClick)
//                    .setNegativeButton("No", negativeButtonClick).show()

//                val layout = LayoutInflater.from(context).inflate(R.layout.fragment_first, parent, false)
                val layout = FirstFragment.viewpagerView
                var viewpagerView = layout.findViewById<ViewPager2>(R.id.viewpager)
                var index = viewpagerView.currentItem
                viewholder.unbind(index)
                notifyDataSetChanged()
            }
        })
        return viewholder
    }

    override fun getItemCount(): Int {
        var count: Int = 0
        when(job) {
            0 -> count = developers.size
            1 -> count = designers.size
            2 -> count = engineers.size
            3 -> count = pms.size
            4 -> count = etcs.size
        }
        return count
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(job) {
            0 -> holder.bind(developers[position])
            1 -> holder.bind(designers[position])
            2 -> holder.bind(engineers[position])
            3 -> holder.bind(pms[position])
            4 -> holder.bind(etcs[position])
        }
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

        fun unbind(position: Int) {
            when(job) {
                0 -> developers.removeAt(position)
                1 -> designers.removeAt(position)
                2 -> engineers.removeAt(position)
                3 -> pms.removeAt(position)
                4 -> etcs.removeAt(position)
            }
        }

    }
}

//    deleteButton = view.findViewById(R.id.delete)
//
//
//    deleteButton.setOnClickListener(object: View.OnClickListener{
//        override fun onClick(v: View?) {
//            val jobCategory = spinner.selectedItemPosition
//            when(jobCategory) {
//                0 -> {
//                    var index = viewpagerView.currentItem
//                    developerList.removeAt(index)
//                    viewpager.adapter=developerAdapter
//                    if (index > developerList.size) {
//                        viewpagerView.currentItem = developerList.size - 1
//                    }
//                    else {
//                        viewpagerView.currentItem = index
//                    }
//                    viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
//                }
//                1 -> {
//                    var index = viewpagerView.currentItem
//                    designerList.removeAt(index)
//                    viewpager.adapter=designerAdapter
//                    if (index > developerList.size) {
//                        viewpagerView.currentItem = developerList.size - 1
//                    }
//                    else {
//                        viewpagerView.currentItem = index
//                    }
//                    viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
//                }
//                2 -> {
//                    var index = viewpagerView.currentItem
//                    engineerList.removeAt(index)
//                    viewpager.adapter=engineerAdapter
//                    if (index > developerList.size) {
//                        viewpagerView.currentItem = developerList.size - 1
//                    }
//                    else {
//                        viewpagerView.currentItem = index
//                    }
//                    viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
//                }
//                3 -> {
//                    var index = viewpagerView.currentItem
//                    pmList.removeAt(index)
//                    viewpager.adapter=pmAdapter
//                    if (index > developerList.size) {
//                        viewpagerView.currentItem = developerList.size - 1
//                    }
//                    else {
//                        viewpagerView.currentItem = index
//                    }
//                    viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
//                }
//                4 -> {
//                    var index = viewpagerView.currentItem
//                    etcList.removeAt(index)
//                    viewpager.adapter=etcAdapter
//                    if (index > developerList.size) {
//                        viewpagerView.currentItem = developerList.size - 1
//                    }
//                    else {
//                        viewpagerView.currentItem = index
//                    }
//                    viewpagerView.orientation=ViewPager2.ORIENTATION_HORIZONTAL
//                }
//            }
//
//
//        }
//    })
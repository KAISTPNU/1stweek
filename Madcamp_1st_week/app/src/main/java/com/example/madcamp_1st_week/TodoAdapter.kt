package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.madcamp_1st_week.databinding.ProjectTodoBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text

class TodoAdapter (private val context: Context): RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    var todoList = mutableListOf<String>()
    var checkedNum = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        var todoBinding = ProjectTodoBinding.inflate(LayoutInflater.from(context))
        todoBinding.todoText.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                val checked = todoBinding.todoText.isChecked
                when(checked) {
                    true -> {
                        todoBinding.todoText.setTextColor(ContextCompat.getColor(context, R.color.gray))
                        checkedNum += 1

//                        setDataToPieChart(ProjectAdapter.fragment.projectItemAfterFolding.chart, 1400)
//                        ProjectAdapter.fragment.projectItemAfterFolding.chart.invalidate()
                        notifyDataSetChanged()
                    }
                    false -> {
                        todoBinding.todoText.setTextColor(ContextCompat.getColor(context, R.color.darknavy))
                        checkedNum -= 1
//                        setDataToPieChart(ProjectAdapter.fragment.projectItemAfterFolding.chart, 1400)
//                        ProjectAdapter.fragment.projectItemAfterFolding.chart.invalidate()
                        notifyDataSetChanged()
                    }
                }
            }
        })
        return ViewHolder(todoBinding.root)
    }

    fun getdid(): Float {
        return (checkedNum.toFloat()/ProjectAdapter.total) * 100
    }

//    fun setDataToPieChart(pieChart: PieChart, duration:Int) {
//        pieChart.setUsePercentValues(true)
//        val dataEntries = ArrayList<PieEntry>()
//
//        println("checkedNum : ${checkedNum}")
//        var did = ((checkedNum.toFloat()/ProjectAdapter.total) * 100)
//        var notdid = (100-did)
//        println("did : ${did}")
//        dataEntries.add(PieEntry(did, ""))
//        dataEntries.add(PieEntry(notdid, ""))
//
//        val colors: ArrayList<Int> = ArrayList()
//        colors.add(ContextCompat.getColor(context, R.color.samsung))
//        colors.add(ContextCompat.getColor(context, R.color.gray))
//
//        val dataSet = PieDataSet(dataEntries, "")
//        val data = PieData(dataSet)
//
//        // In Percentage
//        data.setValueFormatter(PercentFormatter())
//        dataSet.sliceSpace = 0f
//        dataSet.colors = colors
//        pieChart.data = data
//        data.setValueTextSize(0f)
//        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
//        pieChart.animateY(duration, Easing.EaseInOutQuad)
//
//        //create hole in center
//        pieChart.holeRadius = 80f
//        pieChart.transparentCircleRadius = 61f
//        pieChart.isDrawHoleEnabled = true
//        pieChart.setHoleColor(ContextCompat.getColor(context, R.color.white))
//
//
//        //add text in center
//        pieChart.setDrawCenterText(true);
//        pieChart.setCenterTextSize(12f)
//
//        pieChart.invalidate()
//    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.bind(todoList[position])
        println("position : ${todoList[position]}")
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = itemView.findViewById<TextView>(R.id.todo_text)
        fun bind(item: String) {
            view.text = item
        }

    }
}

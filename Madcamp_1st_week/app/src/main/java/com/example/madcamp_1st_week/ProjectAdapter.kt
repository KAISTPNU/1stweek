package com.example.madcamp_1st_week

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcamp_1st_week.databinding.ProjectItemBinding
import com.example.madcamp_1st_week.databinding.ProjectItemTitleBinding
import com.example.madcamp_1st_week.databinding.ProjectTodoBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

/*
    ProjectItem을 처리하기 위한 Adapter Class
 */
class ProjectAdapter(private val context: Context):
    BaseAdapter() {
    var itemList = mutableListOf<ProjectItem>()
    var checkedNum = 0
    private lateinit var todoAdapter: TodoAdapter

//    init { todoAdapter = TodoAdapter(context) }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var projectItemBinding = ProjectItemBinding.inflate(LayoutInflater.from(context)) // view 처리를 위한 Binding

        val projectItem = itemList[position] // 현재 ProjectItem을 가져옴
        todoAdapter = TodoAdapter(context)

        todoAdapter.todoList = projectItem.todo

        projectItemBinding.projectItemBeforeFolding.projectTitle.text = projectItem.title
        projectItemBinding.projectItemBeforeFolding.projectLeader.text = projectItem.leader
        projectItemBinding.projectItemBeforeFolding.projectDDay.text = "D - " + projectItem.d_day.toString()
        setProjectProgressBar(projectItemBinding.projectItemBeforeFolding, projectItem.status)

        projectItemBinding.projectItemAfterFolding.name.text = projectItem.leader
        projectItemBinding.projectItemAfterFolding.phone.text = projectItem.phone
        projectItemBinding.projectItemAfterFolding.participants.text = projectItem.participants
        projectItemBinding.projectItemAfterFolding.todoList.adapter = todoAdapter
        projectItemBinding.projectItemAfterFolding.todoList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        fragment = projectItemBinding
        total = projectItem.todo.size

        initPieChart(projectItemBinding.projectItemAfterFolding.chart)
        setDataToPieChart(projectItemBinding.projectItemAfterFolding.chart, 1400)

        projectItemBinding.foldingCell.setOnClickListener(View.OnClickListener { view->
            projectItemBinding.foldingCell.toggle(true)
            projectItemBinding.projectItemAfterFolding.temp.visibility = CardView.VISIBLE
        })


        when(projectItem.language) { // 프로젝트 언어별로 색상을 다르게 지정
            "Python" -> {
                projectItemBinding.projectItemBeforeFolding.projectTitleBorder
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.samsung))
                projectItemBinding.projectItemBeforeFolding.projectProgress.progressTintList = ColorStateList
                    .valueOf(ContextCompat.getColor(context, R.color.samsung))
                projectItemBinding.projectItemAfterFolding.projectTitleBorder
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.samsung))
            }
            "Kotlin" -> {
                projectItemBinding.projectItemBeforeFolding.projectTitleBorder
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.kakao))
                projectItemBinding.projectItemBeforeFolding.projectProgress.progressTintList = ColorStateList
                    .valueOf(ContextCompat.getColor(context, R.color.kakao))
                projectItemBinding.projectItemAfterFolding.projectTitleBorder
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.kakao))
            }
        }

        return projectItemBinding.root
    }
    fun initPieChart(pieChart: PieChart) {
        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)

        pieChart.setUsePercentValues(true)
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
    }

    fun setDataToPieChart(pieChart: PieChart, duration:Int) {
        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()

        checkedNum = todoAdapter.checkedNum
        println("checkedNum : ${checkedNum}")
        var did = todoAdapter.getdid()
        var notdid = (100-did).toFloat()
        println("did : ${did}")
        dataEntries.add(PieEntry(did, ""))
        dataEntries.add(PieEntry(notdid, ""))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(context, R.color.samsung))
        colors.add(ContextCompat.getColor(context, R.color.gray))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 0f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(0f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(duration, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 80f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(ContextCompat.getColor(context, R.color.white))


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.setCenterTextSize(12f)

        pieChart.invalidate()
    }


    /*
        프로젝트 아이템의 Progress 바를 설정해주는 함수
     */
    fun setProjectProgressBar(binding: ProjectItemTitleBinding, status: Int) {
        var progressBarAnimation = ObjectAnimator
            .ofInt(binding.projectProgress, "progress", 0, status)
        // Progress 바의 애니메이션을 지정
        // 0부터 status값까지 채우는 애니메이션

        progressBarAnimation.setDuration(1000) // 애니메이션 실행 시간 지정 (단위는 ms)
        progressBarAnimation.start()
    }


    companion object{
        lateinit var fragment : ProjectItemBinding
        var position : Int = 0
        var total : Int = 0
    }
}

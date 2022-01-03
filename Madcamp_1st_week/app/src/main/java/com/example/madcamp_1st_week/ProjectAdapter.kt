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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.madcamp_1st_week.databinding.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.ramotion.foldingcell.FoldingCell

/*
    ProjectItem을 처리하기 위한 Adapter Class
 */
class ProjectAdapter(private val context: Context):
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {
    private lateinit var viewpagerBinding : ProjectItemBinding
    private lateinit var todoAdapter : TodoAdapter
    var itemList = mutableListOf<ProjectItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectAdapter.ViewHolder {
        viewpagerBinding = ProjectItemBinding.inflate(LayoutInflater.from(context))
        projectItemBinding = viewpagerBinding

        val view = LayoutInflater.from(context).inflate(R.layout.project_item, parent, false)
        fragment.add(viewpagerBinding)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val title: TextView = itemView.findViewById(R.id.project_title)
//        private val backname: TextView = itemView.findViewById(R.id.leader_name)
//        private val frontname: TextView = itemView.findViewById(R.id.project_leader)
//        private val dday: TextView = itemView.findViewById(R.id.project_d_day)
//        private val phone: TextView = itemView.findViewById(R.id.phone)
//        private val email: TextView = itemView.findViewById(R.id.email)
//        private val participants: TextView = itemView.findViewById(R.id.participants)
//        private val chart: PieChart = itemView.findViewById(R.id.chart)
//        private val todo: RecyclerView = itemView.findViewById(R.id.todoList)
//        private val fold: FoldingCell = itemView.findViewById(R.id.folding_cell)
        private val title: TextView = viewpagerBinding.projectItemBeforeFolding.projectTitle
        private val backname: TextView = viewpagerBinding.projectItemAfterFolding.leaderName
        private val frontname: TextView = viewpagerBinding.projectItemBeforeFolding.projectLeader
        private val dday: TextView = viewpagerBinding.projectItemBeforeFolding.projectDDay
        private val phone: TextView = viewpagerBinding.projectItemAfterFolding.phone
        private val email: TextView = viewpagerBinding.projectItemAfterFolding.email
        private val participants: TextView = viewpagerBinding.projectItemAfterFolding.participants
        private val chart: PieChart = viewpagerBinding.projectItemAfterFolding.chart
        private val todo: RecyclerView = viewpagerBinding.projectItemAfterFolding.todoList
        private val fold: FoldingCell = viewpagerBinding.foldingCell




        fun bind(item: ProjectItem) {
            title.text = item.title
            backname.text = item.leader
            frontname.text = item.leader
            dday.text = "D-" + item.d_day.toString()
            phone.text = item.phone
            email.text = item.email
            participants.text = item.participants
            initPieChart(chart)
            setDataToPieChart(chart, 1400)

            var todoAdapter = TodoAdapter(context)
            todoAdapter.todoList = item.todo
            todo.adapter = todoAdapter
            fold.setOnClickListener(View.OnClickListener { view ->
                this.fold.toggle(false)
            })
            todoAdapter = TodoAdapter(context)
            todoAdapter.todoList = item.todo
            todoAdapter.total = item.todo.size
            todo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            todo.adapter = todoAdapter

            totals.add(item.todo.size)

        }

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

//        checkedNum = todoAdapter.checkedNum
//        println("checkedNum : ${checkedNum}")
//        var did = todoAdapter.getdid()
//        var notdid = (100-did).toFloat()
//        println("did : ${did}")
        dataEntries.add(PieEntry(35f, ""))
        dataEntries.add(PieEntry(65f, ""))

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
    companion object {
        var currentItem  = 0
        var fragment = mutableListOf<ProjectItemBinding>()
        var totals = mutableListOf<Int>()
        lateinit var projectItemBinding: ProjectItemBinding
    }
}

//var itemList = mutableListOf<ProjectItem>()
//var checkedNum = 0
//private lateinit var todoAdapter: TodoAdapter
//
//override fun getCount(): Int {
//    return itemList.size
//}
//
//override fun getItem(position: Int): Any {
//    return itemList[position]
//}
//
//override fun getItemId(position: Int): Long {
//    return position.toLong()
//}
//
//override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
//    var projectItemBinding = ProjectItemBinding.inflate(LayoutInflater.from(context)) // view 처리를 위한 Binding
//
//    val projectItem = itemList[position] // 현재 ProjectItem을 가져옴
//    todoAdapter = TodoAdapter(context)
//    todoAdapter.index = position
//    todoAdapter.todoList = projectItem.todo
//
//    projectItemBinding.projectItemBeforeFolding.projectTitle.text = projectItem.title
//    // project 아이템의 title(펼치기 전의 레이아웃)을 현재 프로젝트의 제목으로 설정
//    // 아래 코드들도 거의 동일합니다
//
//    projectItemBinding.projectItemBeforeFolding.projectLeader.text = projectItem.leader
//    projectItemBinding.projectItemBeforeFolding.projectDDay.text = "D - " + projectItem.d_day.toString()
//
//    projectItemBinding.projectItemAfterFolding.name.text = projectItem.leader
//    projectItemBinding.projectItemAfterFolding.phone.text = projectItem.phone
//    projectItemBinding.projectItemAfterFolding.participants.text = projectItem.participants
//
//    setProjectProgressBar(projectItemBinding.projectItemBeforeFolding, projectItem.status)
//
//    projectItemBinding.projectItemAfterFolding.todoList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//    projectItemBinding.projectItemAfterFolding.todoList.adapter = todoAdapter
//
//
//    fragment = projectItemBinding
//    total = projectItem.todo.size
//
//    initPieChart(projectItemBinding.projectItemAfterFolding.chart)
//    setDataToPieChart(projectItemBinding.projectItemAfterFolding.chart, 1400)
//
//    projectItemBinding.foldingCell.setOnClickListener(View.OnClickListener { view->
//        projectItemBinding.foldingCell.toggle(false)
//    })
//
//    when(projectItem.language) { // 프로젝트 언어별로 색상을 다르게 지정
//        "Python" -> {
//            projectItemBinding.projectItemBeforeFolding.projectTitleBorder
//                .setBackgroundColor(ContextCompat.getColor(context, R.color.samsung))
//            projectItemBinding.projectItemBeforeFolding.projectProgress.progressTintList = ColorStateList
//                .valueOf(ContextCompat.getColor(context, R.color.samsung))
//            projectItemBinding.projectItemAfterFolding.projectTitleBorder
//                .setBackgroundColor(ContextCompat.getColor(context, R.color.samsung))
//        }
//        "Kotlin" -> {
//            projectItemBinding.projectItemBeforeFolding.projectTitleBorder
//                .setBackgroundColor(ContextCompat.getColor(context, R.color.kakao))
//            projectItemBinding.projectItemBeforeFolding.projectProgress.progressTintList = ColorStateList
//                .valueOf(ContextCompat.getColor(context, R.color.kakao))
//            projectItemBinding.projectItemAfterFolding.projectTitleBorder
//                .setBackgroundColor(ContextCompat.getColor(context, R.color.kakao))
//        }
//    }
//
//    return projectItemBinding.root
//}
//fun initPieChart(pieChart: PieChart) {
//    pieChart.setUsePercentValues(true)
//    pieChart.description.text = ""
//    pieChart.isDrawHoleEnabled = false
//    pieChart.setTouchEnabled(false)
//
//    pieChart.setUsePercentValues(true)
//    pieChart.setDrawEntryLabels(false)
//    pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
//    pieChart.legend.isWordWrapEnabled = true
//}
//
//fun setDataToPieChart(pieChart: PieChart, duration:Int) {
//    pieChart.setUsePercentValues(true)
//    val dataEntries = ArrayList<PieEntry>()
//
//    checkedNum = todoAdapter.checkedNum
//    println("checkedNum : ${checkedNum}")
//    var did = todoAdapter.getdid()
//    var notdid = (100-did).toFloat()
//    println("did : ${did}")
//    dataEntries.add(PieEntry(did, ""))
//    dataEntries.add(PieEntry(notdid, ""))
//
//    val colors: ArrayList<Int> = ArrayList()
//    colors.add(ContextCompat.getColor(context, R.color.samsung))
//    colors.add(ContextCompat.getColor(context, R.color.gray))
//
//    val dataSet = PieDataSet(dataEntries, "")
//    val data = PieData(dataSet)
//
//    // In Percentage
//    data.setValueFormatter(PercentFormatter())
//    dataSet.sliceSpace = 0f
//    dataSet.colors = colors
//    pieChart.data = data
//    data.setValueTextSize(0f)
//    pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
//    pieChart.animateY(duration, Easing.EaseInOutQuad)
//
//    //create hole in center
//    pieChart.holeRadius = 80f
//    pieChart.transparentCircleRadius = 61f
//    pieChart.isDrawHoleEnabled = true
//    pieChart.setHoleColor(ContextCompat.getColor(context, R.color.white))
//
//
//    //add text in center
//    pieChart.setDrawCenterText(true);
//    pieChart.setCenterTextSize(12f)
//
//    pieChart.invalidate()
//}
//
//
///*
//    프로젝트 아이템의 Progress 바를 설정해주는 함수
// */
//fun setProjectProgressBar(binding: ProjectItemTitleBinding, status: Int) {
//    var progressBarAnimation = ObjectAnimator
//        .ofInt(binding.projectProgress, "progress", 0, status)
//    // Progress 바의 애니메이션을 지정
//    // 0부터 status값까지 채우는 애니메이션
//
//    progressBarAnimation.setDuration(1000) // 애니메이션 실행 시간 지정 (단위는 ms)
//    progressBarAnimation.start()
//}
//
//companion object{
//    lateinit var fragment : ProjectItemBinding
//    var position : Int = 0
//    var total : Int = 0
//}
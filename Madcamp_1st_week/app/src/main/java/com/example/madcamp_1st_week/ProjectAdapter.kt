package com.example.madcamp_1st_week

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
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

class ProjectAdapter(private val context: Context):
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    private lateinit var viewpagerBinding: ProjectItemBinding
    var itemList = mutableListOf<ProjectItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectAdapter.ViewHolder {
        viewpagerBinding = ProjectItemBinding.inflate(LayoutInflater.from(context), parent, false)
        projectItemBinding = viewpagerBinding


        val view = LayoutInflater.from(context).inflate(R.layout.project_item, parent, false)
//        fragment.add(viewpagerBinding)
        return ViewHolder(viewpagerBinding)
    }

    override fun onBindViewHolder(holder: ProjectAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class ViewHolder(binding: ProjectItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title: TextView = binding.projectItemBeforeFolding.projectTitle
        private val backname: TextView = binding.projectItemAfterFolding.leaderName
        private val frontname: TextView = binding.projectItemBeforeFolding.projectLeader
        private val dday: TextView = binding.projectItemBeforeFolding.projectDDay
        private val phone: TextView = binding.projectItemAfterFolding.phone
        private val email: TextView = binding.projectItemAfterFolding.email
        private val end_date: TextView = binding.projectItemBeforeFolding.projectEndDate

        private var language: String = ""
        private val participants: TextView = binding.projectItemAfterFolding.participants
        private val chart: PieChart = binding.projectItemAfterFolding.chart
        private val cb1: CheckBox = binding.projectItemAfterFolding.checkbox1
        private val cb2: CheckBox = binding.projectItemAfterFolding.checkbox2
        private val cb3: CheckBox = binding.projectItemAfterFolding.checkbox3
        private val cb4: CheckBox = binding.projectItemAfterFolding.checkbox4
        private val cb5: CheckBox = binding.projectItemAfterFolding.checkbox5

        //        private val fold: FoldingCell = binding.foldingCell
        private val fold: RelativeLayout = binding.foldingCell

        private val frontBorder = binding.projectItemBeforeFolding.projectTitleBorder
        private val backBorder = binding.projectItemAfterFolding.projectTitleBorder

        private val titleView = binding.cellTitleView
        private val backView = binding.cellContentView
        var checkedNum = 0

        fun bind(item: ProjectItem) {
            title.text = item.title
            backname.text = item.leader
            frontname.text = item.leader
            dday.text = "D-" + item.d_day.toString()
            phone.text = item.phone
            email.text = item.email
            participants.text = item.participants
            end_date.text = item.end_date.toString()
            title.setSingleLine(true)
            title.setEllipsize(TextUtils.TruncateAt.MARQUEE)
            title.setSelected(true)


            language = item.language

            initPieChart(chart)
            when (item.language.uppercase()) { // 프로젝트 언어별로 색상을 다르게 지정
                "PYTHON" -> {
                    viewpagerBinding.projectItemBeforeFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.python))
                    viewpagerBinding.projectItemBeforeFolding.projectProgress.progressTintList =
                        ColorStateList
                            .valueOf(ContextCompat.getColor(context, R.color.python))
                    viewpagerBinding.projectItemAfterFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.python))
                    setDataToPieChart(chart, 1400, 0, R.color.python)
                    viewpagerBinding.projectItemBeforeFolding.projectItemVerticalBar.verticalBar
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.python))
                }
                "KOTLIN" -> {
                    viewpagerBinding.projectItemBeforeFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.kotlin))
                    viewpagerBinding.projectItemBeforeFolding.projectProgress.progressTintList =
                        ColorStateList
                            .valueOf(ContextCompat.getColor(context, R.color.kotlin))
                    viewpagerBinding.projectItemAfterFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.kotlin))
                    setDataToPieChart(chart, 1400, 0, R.color.kotlin)
                    viewpagerBinding.projectItemBeforeFolding.projectItemVerticalBar.verticalBar
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.kotlin))
                }
                else -> {
                    viewpagerBinding.projectItemBeforeFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.others))
                    viewpagerBinding.projectItemBeforeFolding.projectProgress.progressTintList =
                        ColorStateList
                            .valueOf(ContextCompat.getColor(context, R.color.others))
                    viewpagerBinding.projectItemAfterFolding.projectTitleBorder
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.others))
                    setDataToPieChart(chart, 1400, 0, R.color.others)
                    viewpagerBinding.projectItemBeforeFolding.projectItemVerticalBar.verticalBar
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.others))
                }
            }
            cb1.text = item.todo[0]
            cb2.text = item.todo[1]
            cb3.text = item.todo[2]
            cb4.text = item.todo[3]
            cb5.text = item.todo[4]
            fold.setOnClickListener(View.OnClickListener { view ->
                when (backView.visibility) {
                    FrameLayout.GONE -> {
                        var animation =
                            AnimationUtils.loadAnimation(context, R.anim.project_item_slide_out_top)
                        animation.duration = 300
                        animation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                titleView.visibility = FrameLayout.GONE
                                backView.visibility = FrameLayout.VISIBLE
                                fold.startAnimation(
                                    AnimationUtils.loadAnimation(
                                        context,
                                        R.anim.project_item_slide_in_top
                                    )
                                )
                            }

                            override fun onAnimationRepeat(p0: Animation?) {}
                        })
                        fold.startAnimation(animation)
                    }
                    else -> {
                        var animation =
                            AnimationUtils.loadAnimation(context, R.anim.project_item_slide_out_top)
                        animation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {}

                            override fun onAnimationEnd(p0: Animation?) {
                                backView.visibility = FrameLayout.GONE
                                titleView.visibility = FrameLayout.VISIBLE
                                var anim = AnimationUtils.loadAnimation(
                                    context,
                                    R.anim.project_item_slide_in_top
                                )
                                anim.duration = 300
                                fold.startAnimation(anim)
                            }

                            override fun onAnimationRepeat(p0: Animation?) {}
                        })
                        fold.startAnimation(animation)
                    }
                }
            })
            listen(cb1, chart)
            listen(cb2, chart)
            listen(cb3, chart)
            listen(cb4, chart)
            listen(cb5, chart)
        }

        fun listen(cb: CheckBox, chart: PieChart) {
            cb.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    val checked = cb.isChecked
                    when (checked) {
                        true -> {
                            cb.setTextColor(ContextCompat.getColor(context, R.color.gray))
                            checkedNum += 1
                            println("checkedNum: ${checkedNum}")

                            when(language.uppercase()) {
                                "PYTHON" -> {
                                    cb.buttonTintList = ContextCompat.getColorStateList(context, R.color.python)
                                    setDataToPieChart(chart, 1400, checkedNum, R.color.python)
                                }
                                "KOTLIN" -> {
                                    cb.buttonTintList = ContextCompat.getColorStateList(context, R.color.kotlin)
                                    setDataToPieChart(chart, 1400, checkedNum, R.color.kotlin)
                                }
                            }
                        }
                        false -> {
                            cb.setTextColor(ContextCompat.getColor(context, R.color.darknavy))
                            cb.buttonTintList = ContextCompat.getColorStateList(context, R.color.gray)
                            checkedNum -= 1
                            println("checkedNum: ${checkedNum}")
                            when(language.uppercase()) {
                                "PYTHON" -> setDataToPieChart(chart, 1400, checkedNum, R.color.python)
                                "KOTLIN" -> setDataToPieChart(chart, 1400, checkedNum, R.color.kotlin)
                            }
                        }
                    }
                }
            })
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

    fun setDataToPieChart(pieChart: PieChart, duration: Int, checkedNum: Int, color: Int) {
        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()

        var did = (checkedNum.toFloat() / 5) * 100
        var notdid = (100 - did)

        dataEntries.add(PieEntry(did, ""))
        dataEntries.add(PieEntry(notdid, ""))


        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(context, color))
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
        pieChart.holeRadius = 50f
        pieChart.transparentCircleRadius = 63f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(ContextCompat.getColor(context, R.color.white))


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.setCenterTextSize(12f)
        pieChart.setCenterTextTypeface(context.resources.getFont(R.font.uber_move_medium))
        pieChart.centerText = (did).toInt().toString() + "%"

        pieChart.legend.isEnabled = false
        pieChart.invalidate()
    }

    companion object {
        lateinit var projectItemBinding: ProjectItemBinding
    }
}
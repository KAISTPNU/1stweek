package com.example.madcamp_1st_week

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.madcamp_1st_week.databinding.FragmentThirdBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDate.of
import java.util.*
import java.util.EnumSet.of
import kotlin.collections.ArrayList

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    private lateinit var projectAdapter: ProjectAdapter
    val projectList = mutableListOf<ProjectItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        projectAdapter = ProjectAdapter(this.requireContext())

        for (i in 1..2) {
            projectList.add(ProjectItem("MadCamp 1st Week Proj.",
                                    "Python",
                                       "Juhyeon Lee",
                                         78,
                                        LocalDate.of(2021, 12, 28),
                                        LocalDate.of(2022, 1, 4)))
        }
        for (i in 1..2) {
            projectList.add(ProjectItem("MadCamp 1st Week Proj.",
                "Kotlin",
                "Junyoung Lee",
                78,
                LocalDate.of(2021, 12, 28),
                LocalDate.of(2022, 1, 4)))
        }
        projectAdapter.itemList = projectList
        binding.projectList.adapter = projectAdapter

        initPieChart(binding.pieChart1)
        setDataToPieChart(binding.pieChart1, 1400)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initPieChart(binding.pieChart2)
        //setDataToPieChart(binding.pieChart2, 1400)
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
        dataEntries.add(PieEntry(45f, "Kotlin"))
        dataEntries.add(PieEntry(28f, "Python"))
        dataEntries.add(PieEntry(27f, "C/C++"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(requireContext(), R.color.samsung))
        colors.add(ContextCompat.getColor(requireContext(), R.color.kakao))
        colors.add(ContextCompat.getColor(requireContext(), R.color.darknavy))

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
        pieChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.white))


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Project\nLanguage Ratio"
        pieChart.setCenterTextSize(12f)
        pieChart.setCenterTextTypeface(resources.getFont(R.font.uber_move_medium))

        pieChart.invalidate()
    }


}
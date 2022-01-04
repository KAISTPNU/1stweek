package com.example.madcamp_1st_week

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcamp_1st_week.databinding.FragmentThirdBinding
import com.example.madcamp_1st_week.databinding.ProjectItemBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDate.of
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.EnumSet.of
import kotlin.collections.ArrayList

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    private lateinit var projectAdapter: ProjectAdapter
    private lateinit var jsonArray: JSONArray
    val projectList = mutableListOf<ProjectItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        readJsonData()
        projectList.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*
            스마트폰 상태바의 색상을 바꿔서 마치 전체화면처럼 보이게 합니다
         */
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.lightgray)

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        fragmentThirdBinding = binding
        projectAdapter = ProjectAdapter(this.requireContext())

        projectAdapter.itemList = projectList
        binding.projectList.adapter = projectAdapter
        val args = arguments
        if (args != null) {
            var jsonString = args.getString("project")
            var jsonObject = JSONObject(jsonString)
            var name = jsonObject.getString("name")
            var email = jsonObject.getString("email")
            var phone = jsonObject.getString("phone")
            var title = jsonObject.getString("title")
            var language = jsonObject.getString("language")
            var startDate = jsonObject.getString("startdate")
            var endDate = jsonObject.getString("enddate")
            var participants = jsonObject.getString("participants")
            var todo1 = jsonObject.getString("todo1")
            var todo2 = jsonObject.getString("todo2")
            var todo3 = jsonObject.getString("todo3")
            var todo4 = jsonObject.getString("todo4")
            var todo5 = jsonObject.getString("todo5")
            var todoList = listOf(todo1, todo2, todo3, todo4, todo5)
            val range = (0..100)
            var status = range.random()

            var item = ProjectItem(title, language, name, status
                , LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-M-d"))
                , LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-M-d")), participants, todoList, email, phone)
            projectList.add(item)
            writeJsonData(item)
            }


        initPieChart(binding.pieChart1)
        setDataToPieChart(binding.pieChart1, 1400)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun readJsonData() {
        projectList.clear()
        var jsonFile = File(context?.filesDir, "project.json")
        when (jsonFile.exists()) {
            true -> {
                var reader = FileReader(jsonFile)
                var jsonString = BufferedReader(reader).readText()
                if (!jsonString.isNullOrBlank()) {
                    jsonArray = JSONArray(jsonString)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val title = jsonObject.getString("title")
                        val leader = jsonObject.getString("name")
                        val language = jsonObject.getString("language")
                        val status = jsonObject.getString("status").toInt()
                        val startDate = jsonObject.getString("startDate")
                        val endDate = jsonObject.getString("endDate")
                        val participants = jsonObject.getString("participants")
                        val todo = jsonObject.getString("todo").split(", ", "[", "]")
                        val email = jsonObject.getString("email")
                        val phone = jsonObject.getString("phone")

                        projectList.add(ProjectItem(title, language, leader, status
                            , LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-M-d"))
                            , LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-M-d"))
                            , participants, todo, email, phone))
                    }
                }
            }
            false -> File(context?.filesDir, "project.json").createNewFile()
        }
    }

    fun writeJsonData(item: ProjectItem) {
        var jsonFile = File(context?.filesDir, "project.json")
        var writer = BufferedWriter(FileWriter(jsonFile))
        var jsonArray = JSONArray()
        var jsonObject = JSONObject()
        jsonObject.put("title", item.title)
        jsonObject.put("language", item.language)
        jsonObject.put("name", item.leader)
        jsonObject.put("email", item.email)
        jsonObject.put("phone", item.phone)
        jsonObject.put("status", item.status)
        jsonObject.put("startDate", item.start_date)
        jsonObject.put("endDate", item.end_date)
        jsonObject.put("todo", item.todo)
        jsonObject.put("participants", item.participants)



        jsonArray.put(jsonObject)
        writer.write(jsonArray.toString())
        writer.close()
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
        colors.add(ContextCompat.getColor(requireContext(), R.color.kotlin))
        colors.add(ContextCompat.getColor(requireContext(), R.color.python))
        colors.add(ContextCompat.getColor(requireContext(), R.color.others))

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
        pieChart.holeRadius = 30f
        pieChart.transparentCircleRadius = 51f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.white))


        //add text in center
        pieChart.setDrawCenterText(false)
//        pieChart.centerText = "Project\nLanguage Ratio"
//        pieChart.setCenterTextSize(12f)
//        pieChart.setCenterTextTypeface(resources.getFont(R.font.uber_move_medium))

        pieChart.legend.isEnabled = false
        pieChart.invalidate()
    }
    companion object {
        lateinit var fragmentThirdBinding: FragmentThirdBinding
    }

}
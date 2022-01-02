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
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.madcamp_1st_week.databinding.ProjectItemBinding
import com.example.madcamp_1st_week.databinding.ProjectItemTitleBinding

/*
    ProjectItem을 처리하기 위한 Adapter Class
 */
class ProjectAdapter(private val context: Context):
    BaseAdapter() {
    var itemList = mutableListOf<ProjectItem>()
    private lateinit var todoAdapter: TodoAdapter

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
        val projectItem = itemList[position] // 현재 ProjectItem을 가져옴

        todoAdapter = TodoAdapter(context)
        todoAdapter.todoList = projectItem.todo

        var projectItemBinding = ProjectItemBinding.inflate(LayoutInflater.from(context)) // view 처리를 위한 Binding

        projectItemBinding.projectItemBeforeFolding.projectTitle.text = projectItem.title
        // project 아이템의 title(펼치기 전의 레이아웃)을 현재 프로젝트의 제목으로 설정
        // 아래 코드들도 거의 동일합니다

        projectItemBinding.projectItemBeforeFolding.projectLeader.text = projectItem.leader
        projectItemBinding.projectItemBeforeFolding.projectDDay.text = "D - " + projectItem.d_day.toString()

        projectItemBinding.projectItemAfterFolding.name.text = projectItem.leader
        projectItemBinding.projectItemAfterFolding.phone.text = projectItem.phone
        projectItemBinding.projectItemAfterFolding.participants.text = projectItem.participants

        setProjectProgressBar(projectItemBinding.projectItemBeforeFolding, projectItem.status)

        projectItemBinding.projectItemAfterFolding.todoList.adapter = todoAdapter



        projectItemBinding.foldingCell.setOnClickListener(View.OnClickListener { view->
            projectItemBinding.foldingCell.toggle(false)
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
}

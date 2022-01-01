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

class ProjectAdapter(private val context: Context):
    BaseAdapter() {
    var itemList = mutableListOf<ProjectItem>()

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
        val projectItem = itemList[position]
        var projectItemBinding = ProjectItemBinding.inflate(LayoutInflater.from(context))

        projectItemBinding.projectItemTitle.projectTitle.text = projectItem.title
        projectItemBinding.projectItemTitle.projectLeader.text = projectItem.leader
        projectItemBinding.projectItemTitle.projectDDay.text = "D - " + projectItem.d_day.toString()
        setProjectProgressBar(projectItemBinding.projectItemTitle, projectItem.status)

        projectItemBinding.foldingCell.setOnClickListener(View.OnClickListener { view->
            projectItemBinding.foldingCell.toggle(false)
        })

        when(projectItem.language) {
            "Python" -> {
                projectItemBinding.projectItemTitle.projectTitleBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.samsung))
                projectItemBinding.projectItemTitle.projectProgress.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.samsung))
            }
            "Kotlin" -> {
                projectItemBinding.projectItemTitle.projectTitleBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.kakao))
                projectItemBinding.projectItemTitle.projectProgress.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.kakao))
            }
        }

        return projectItemBinding.root
    }

    fun setProjectProgressBar(binding: ProjectItemTitleBinding, status: Int) {
        var progressBarAnimation = ObjectAnimator
            .ofInt(binding.projectProgress, "progress", 0, status)
        progressBarAnimation.setDuration(1000)
        progressBarAnimation.start()
    }
}

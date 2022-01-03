package com.example.madcamp_1st_week

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.madcamp_1st_week.databinding.ProjectTodoBinding
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
                    }
                    false -> {
                        todoBinding.todoText.setTextColor(ContextCompat.getColor(context, R.color.darknavy))
                        checkedNum -= 1
                    }
                }
            }
        })
        return ViewHolder(todoBinding.root)
    }


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

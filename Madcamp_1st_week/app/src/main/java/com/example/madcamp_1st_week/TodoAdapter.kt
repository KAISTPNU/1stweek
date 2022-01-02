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
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text

class TodoAdapter (private val context: Context): RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    var todoList = mutableListOf<String>()
    private lateinit var checkbutton : ImageButton

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.project_todo,parent,false)
        checkbutton = view.findViewById<ImageButton>(R.id.todo_check)
        checkbutton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                view.findViewById<TextView>(R.id.todo_text).setTextColor(ContextCompat.getColor(context, R.color.lightgray))
            }
        })
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val todo: TextView = itemView.findViewById(R.id.todo_text)

        fun bind(item: String) {
            todo.text = item
        }

    }
}

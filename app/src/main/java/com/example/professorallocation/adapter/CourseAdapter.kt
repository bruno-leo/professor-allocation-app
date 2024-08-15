package com.example.professorallocation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.professorallocation.R
import com.example.professorallocation.model.Course
import com.example.professorallocation.holder.CourseHolder

class CourseAdapter(
    var list: List<Course> = emptyList(),
    val onEdit: (id: Int, course: Course) -> Unit,
    val onDelete: (id: Int) -> Unit
) : Adapter<CourseHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return CourseHolder(view)
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {
        val course = list[position]
        holder.configure(course, onEdit, onDelete)
    }

    override fun getItemCount(): Int = list.size

    fun addCourses(courses: List<Course>) {
        list = courses
        notifyDataSetChanged()
    }

    fun updateCourse(id: Int, cursoNovo: Course) {
        val curso = list.find { it.id == id }
        val position = list.indexOf(curso)
        list[position].name = cursoNovo.name
        notifyItemChanged(position)
    }
}
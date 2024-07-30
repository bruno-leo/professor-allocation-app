package com.example.professorallocation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.professorallocation.R
import com.example.professorallocation.model.Course

class CourseAdapter(
    var list: List<Course> = emptyList(),
    val onEdit: (id: Int, course: Course) -> Unit,
    val onDelete: (id: Int) -> Unit
) : Adapter<CourseHolder>() {
    fun addCourses(courses: List<Course>) {
        list = courses
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return CourseHolder(view)
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {
        val course = list[position]
        holder.configure(course, onEdit, onDelete)
    }

    override fun getItemCount(): Int = list.size
}
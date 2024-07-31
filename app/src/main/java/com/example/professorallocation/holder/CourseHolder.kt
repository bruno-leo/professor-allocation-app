package com.example.professorallocation.holder

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.professorallocation.R
import com.example.professorallocation.model.Course

class CourseHolder(rootView: View) : ViewHolder(rootView) {
    var tvView: TextView = rootView.findViewById(R.id.tvTextItem)
    var ibEdit: ImageButton = rootView.findViewById(R.id.ibEdit)
    var ibDelete: ImageButton = rootView.findViewById(R.id.ibDelete)

    fun configure(course: Course, onEdit: (id: Int, course: Course) -> Unit, onDelete: (id: Int) -> Unit) {
        tvView.text = course.name

        ibEdit.setOnClickListener {
            onEdit(course.id!!, course)
        }

        ibDelete.setOnClickListener {
            onDelete(course.id!!)
        }
    }

//    companion object {
//        fun createInstanceHolder(context: Context, parent: ViewGroup): CourseHolder {
//            val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
//            return CourseHolder(view)
//        }
//    }
}
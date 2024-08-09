package com.example.professorallocation.holder

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.professorallocation.R
import com.example.professorallocation.model.Allocation
import java.text.SimpleDateFormat
import java.util.Date

class AllocationHolder(rootView: View) : ViewHolder(rootView) {
    var tvDay: TextView = rootView.findViewById(R.id.tvDay)
    var tvStart: TextView = rootView.findViewById(R.id.tvStart)
    var tvEnd: TextView = rootView.findViewById(R.id.tvEnd)
    var tvProfessorName: TextView = rootView.findViewById(R.id.tvProfessorName)
    var tvCourseName: TextView = rootView.findViewById(R.id.tvCourseName)
    var ibEditAlloc: ImageButton = rootView.findViewById(R.id.ibEditAlloc)
    var ibDeleteAlloc: ImageButton = rootView.findViewById(R.id.ibDeleteAlloc)

    fun configue(
        allocation: Allocation,
        onEdit: (id: Int, allocation: Allocation) -> Unit,
        onDelete: (id: Int) -> Unit
    ) {
        tvDay.text = allocation.day
        tvStart.text = allocation.startHour
        tvEnd.text = allocation.endHour
        tvProfessorName.text = allocation.professor?.name
        tvCourseName.text = allocation.course?.name

        ibEditAlloc.setOnClickListener {
//            onEdit(allocation.id!!, allocation)
        }

        ibDeleteAlloc.setOnClickListener {
            onDelete(allocation.id!!)
        }
    }
}
package com.example.professorallocation.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.professorallocation.R
import com.example.professorallocation.model.Department

class DepartmentHolder(rootView: View) : ViewHolder(rootView) {
    var tvView: TextView = rootView.findViewById(R.id.tvTextItem)
    var ibEdit: ImageButton = rootView.findViewById(R.id.ibEdit)
    var ibDelete: ImageButton = rootView.findViewById(R.id.ibDelete)

    fun configure(department: Department, onEdit: (id: Int, department: Department) -> Unit, onDelete: (id: Int) -> Unit) {
        tvView.text = department.name

        ibEdit.setOnClickListener {
            onEdit(department.id!!, department)
        }

        ibDelete.setOnClickListener {
            onDelete(department.id!!)
        }
    }

//    companion object {
//        fun createInstanceHolder(context: Context, parent: ViewGroup): DepartmentHolder {
//            val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
//            return DepartmentHolder(view)
//        }
//    }
}
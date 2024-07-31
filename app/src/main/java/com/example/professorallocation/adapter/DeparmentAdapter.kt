package com.example.professorallocation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.professorallocation.R
import com.example.professorallocation.model.Department
import com.example.professorallocation.holder.DepartmentHolder

class DeparmentAdapter(
    var list: List<Department> = emptyList(),
    val onEdit: (id: Int, department: Department) -> Unit,
    val onDelete: (id: Int) -> Unit
) : Adapter<DepartmentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DepartmentHolder(view)
    }

    override fun onBindViewHolder(holder: DepartmentHolder, position: Int) {
        val department = list[position]
        holder.configure(department, onEdit, onDelete)
    }

    override fun getItemCount(): Int = list.size

    fun addDepartments(departments: List<Department>) {
        list = departments
        notifyDataSetChanged()
    }
}
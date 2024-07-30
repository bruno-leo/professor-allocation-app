package com.example.professorallocation.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.professorallocation.model.Department

class DeparmentAdapter(
    var list: List<Department> = emptyList(),
    val onEdit: (id: Int, department: Department) -> Unit,
    val onDelete: (id: Int) -> Unit
) : Adapter<DepartmentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentHolder {
        return DepartmentHolder.createInstanceHolder(parent.context, parent)
    }

    override fun onBindViewHolder(holder: DepartmentHolder, position: Int) {
        val department = list[position]
        holder.configure(department, onEdit, onDelete)
    }

    override fun getItemCount(): Int = list.size

    fun addDepartments(departments: List<Department>) {
        list = departments
    }
}
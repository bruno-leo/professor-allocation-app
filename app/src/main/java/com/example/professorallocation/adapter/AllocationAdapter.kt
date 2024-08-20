package com.example.professorallocation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.professorallocation.R
import com.example.professorallocation.holder.AllocationHolder
import com.example.professorallocation.model.Allocation

class AllocationAdapter(
    var list: List<Allocation> = emptyList(),
    val onEdit: (id: Int, allocation: Allocation) -> Unit,
    val onDelete: (id: Int) -> Unit
) : Adapter<AllocationHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllocationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_allocation_layout, parent, false)
        return AllocationHolder(view)
    }

    override fun onBindViewHolder(holder: AllocationHolder, position: Int) {
        val allocation = list[position]
        holder.configue(allocation, onEdit, onDelete)
    }

    override fun getItemCount(): Int = list.size

    fun addAllocations(allocations: List<Allocation>) {
        list = allocations
        notifyDataSetChanged()
    }

    fun updateAllocations(id: Int, newAllocation: Allocation) {
        val allocation = list.find { it.id == id }
        val position = list.indexOf(allocation)
        list[position].day = newAllocation.day
        list[position].startHour = newAllocation.startHour
        list[position].endHour = newAllocation.endHour
        list[position].professor = newAllocation.professor
        list[position].course = newAllocation.course
        notifyItemChanged(position)
    }
}
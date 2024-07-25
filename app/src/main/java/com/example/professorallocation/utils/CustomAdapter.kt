package com.example.professorallocation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R

class CustomAdapter(
    private val list: List<String>
) : RecyclerView.Adapter<CustomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return CustomHolder(layoutView)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.configure(list[position])
    }

    override fun getItemCount(): Int = list.size
}
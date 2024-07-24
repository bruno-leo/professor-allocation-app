package com.example.professorallocation.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R

class CustomHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

    private val tvView: TextView = rootView.findViewById(R.id.tvTextItem)

    fun configure(name: String) {
        tvView.text = name
    }
}
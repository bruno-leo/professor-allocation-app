package com.example.professorallocation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.professorallocation.R
import com.example.professorallocation.model.Department

class DepartmentSpinnerAdapter(
    context: Context,
    private var list: List<Department> = emptyList()
) : ArrayAdapter<Department>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    private fun createViewFromResource(convertView: View?, parent: ViewGroup, position: Int): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_department_layout, parent, false)
        val textView = view.findViewById<TextView>(R.id.tvSpinnerDepartmentName)
        val departments = getItem(position)
        textView.text = departments?.name
        return view
    }
}
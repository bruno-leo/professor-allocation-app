package com.example.professorallocation.holder

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.professorallocation.R
import com.example.professorallocation.model.Professor

class ProfessorHolder(rootView: View) : ViewHolder(rootView) {
    var tvView: TextView = rootView.findViewById(R.id.tvTextItem)
    var ibEdit: ImageButton = rootView.findViewById(R.id.ibEdit)
    var ibDelete: ImageButton = rootView.findViewById(R.id.ibDelete)

    fun configure(
        professor: Professor,
        onEdit: (id: Int, professor: Professor) -> Unit,
        onDelete: (id: Int) -> Unit)
    {
        tvView.text = professor.name

        ibEdit.setOnClickListener {
            onEdit(professor.id!!, professor)
        }

        ibDelete.setOnClickListener {
            onDelete(professor.id!!)
        }
    }
}
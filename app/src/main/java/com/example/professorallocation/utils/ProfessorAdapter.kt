package com.example.professorallocation.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.professorallocation.model.Professor

class ProfessorAdapter(
    var list: List<Professor> = emptyList(),
    val onEdit: (id: Int, professor: Professor) -> Unit,
    val onDelete: (id: Int) -> Unit
) : Adapter<ProfessorHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProfessorHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
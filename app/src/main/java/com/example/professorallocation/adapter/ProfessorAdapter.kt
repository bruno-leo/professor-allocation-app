package com.example.professorallocation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.professorallocation.R
import com.example.professorallocation.model.Professor
import com.example.professorallocation.holder.ProfessorHolder

class ProfessorAdapter(
    var list: List<Professor> = emptyList(),
    val onEdit: (id: Int, professor: Professor) -> Unit,
    val onDelete: (id: Int) -> Unit
) : Adapter<ProfessorHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_professor_layout, parent, false)
        return ProfessorHolder(view)
    }

    override fun onBindViewHolder(holder: ProfessorHolder, position: Int) {
        val professor = list[position]
        holder.configure(professor, onEdit, onDelete)
    }

    override fun getItemCount(): Int = list.size

    fun addProfessors(professors: List<Professor>) {
        list = professors
        notifyDataSetChanged()
    }

    fun updateProfessors(id: Int, newProfessor: Professor) {
        val professor = list.find { it.id == id }
        val position = list.indexOf(professor)
        list[position].name = newProfessor.name
        list[position].cpf = newProfessor.cpf
        list[position].department = newProfessor.department
        notifyItemChanged(position)
    }
}
package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.adapter.ProfessorAdapter
import com.example.professorallocation.model.Professor
import com.example.professorallocation.repository.ProfessorRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfessorActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProfessorAdapter
    private lateinit var repository: ProfessorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = ProfessorRepository(RetrofitConfig.professorService)
        rv = findViewById(R.id.rvProfessorsList)
        adapter = ProfessorAdapter(
            onEdit = { id, professor ->
                updateProfessor(id, professor)
            },
            onDelete = { id ->
                deleteProfessor(id)
            }
        )
        rv.adapter = adapter

        getProfessors(repository)
        addProfessor(repository)
    }

    fun addProfessor(repository: ProfessorRepository) {
        val btAddProfessor = findViewById<FloatingActionButton>(R.id.btAddProfessor)
        btAddProfessor.setOnClickListener {
            addProfessorDialog()
        }
    }

    fun addProfessorDialog() {

    }

    fun getProfessors(repository: ProfessorRepository) {
        return repository.getProfessors(
            onCall = { professors ->
                Log.i(">>>", "success get professors")
                professors?.let { adapter.addProfessors(it) }
            },
            onError = {
                Log.e(">>>", "error get professors $it")
            }
        )
    }

    fun saveProfessor(professor: Professor) = repository.saveProfessor(
        professor,
        {},
        {}
    )

    fun updateProfessor(id: Int, professor: Professor) = repository.updateProfessor(
        id,
        professor,
        {},
        {}
    )

    fun deleteProfessor(id: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm deletion")
        alertDialog.setMessage("Are you sure you want to delete this item?")

        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        alertDialog.setPositiveButton("Confirm") { dialog, which ->
            repository.deleteProfessor(id, {}, {})
        }

        alertDialog.show()
    }
}
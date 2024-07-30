package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.repository.ProfessorRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.example.professorallocation.utils.CourseAdapter
import com.example.professorallocation.utils.ProfessorAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfessorActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProfessorAdapter
    private lateinit var repository: ProfessorRepository

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = ProfessorRepository(RetrofitConfig.professorService)
        rv = findViewById(R.id.rvProfessorsList)
        adapter = ProfessorAdapter(

        )
        rv.adapter = adapter

        getProfessors(repository)
        addProfessor(repository)
    }*/

    /*fun addProfessor(repository: ProfessorRepository) {
        val btAddProfessor = findViewById<FloatingActionButton>(R.id.btAddProfessor)
        btAddProfessor.setOnClickListener {
            addProfessorDialog()
        }
    }*/

    /*fun addProfessorDialog() {}*/

    /*fun getProfessors(repository: ProfessorRepository) {
        return repository.getProfessors(
            onCall = { professors ->
                Log.i(">>>", "success get professors")
//                val rvList = findViewById<RecyclerView>(R.id.rvProfessorsList)
//                rvList.adapter = professors?.map { it.name }?.let { CourseAdapter(it) }
                professors?.let {  }
            },
            onError = {
                Log.e(">>>", "error get professors $it")
            }
        )
    }*/
}
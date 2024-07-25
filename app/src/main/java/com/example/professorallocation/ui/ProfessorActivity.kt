package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.repository.ProfessorRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.example.professorallocation.utils.CustomAdapter

class ProfessorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val professorRepository = ProfessorRepository(RetrofitConfig.professorService)

        getProfessors(professorRepository)
    }

    private fun getProfessors(repository: ProfessorRepository) {
        return repository.getProfessors(
            onCall = { professors ->
                Log.i(">>>", "success get professors")
                val rvList = findViewById<RecyclerView>(R.id.rvProfessorsList)
                rvList.adapter = professors?.map { it.name }?.let { CustomAdapter(it) }
            },
            onError = {
                Log.e(">>>", "error get professors $it")
            }
        )
    }
}
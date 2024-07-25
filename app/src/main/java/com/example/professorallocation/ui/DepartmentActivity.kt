package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.repository.DepartmentRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.example.professorallocation.utils.CustomAdapter

class DepartmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val departmentRepository = DepartmentRepository(RetrofitConfig.departmentService)

        getDepartments(departmentRepository)
    }

    private fun getDepartments(repository: DepartmentRepository) {
        return repository.getDepartments(
            onCall = { departments ->
                Log.i(">>>", "success get departments")
                val rvList = findViewById<RecyclerView>(R.id.rvDepartmentList)
                rvList.adapter = departments?.map { it.name }?.let { CustomAdapter(it) }
            },
            onError = {
                Log.e(">>>", "error get departments $it")
            }
        )
    }
}
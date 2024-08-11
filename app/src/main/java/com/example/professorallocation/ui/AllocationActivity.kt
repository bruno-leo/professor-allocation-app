package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.adapter.AllocationAdapter
import com.example.professorallocation.repository.AllocationRepository
import com.example.professorallocation.repository.CourseRepository
import com.example.professorallocation.repository.ProfessorRepository
import com.example.professorallocation.repository.RetrofitConfig

class AllocationActivity : MainActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: AllocationAdapter
    private lateinit var repository: AllocationRepository
    private lateinit var professorRepository: ProfessorRepository
    private lateinit var courseRepository: CourseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allocation)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = AllocationRepository(RetrofitConfig.allocationService)
        professorRepository = ProfessorRepository(RetrofitConfig.professorService)
        courseRepository = CourseRepository(RetrofitConfig.courseService)

        rv = findViewById(R.id.rvAllocationList)
        adapter = AllocationAdapter(
            onEdit = { id, allocation ->

            },
            onDelete = { id ->
                deleteAllocation(id)
            }
        )
        rv.adapter = adapter

        getAllocations()
    }

    fun getAllocations() {
        return repository.getAllocations(
            onCall = { allocations ->
                Log.i(">>>", "success get allocations")
                allocations?.let { adapter.addAllocations(it) }
            },
            onError = {
                Log.e(">>>", "error get allocations $it")
            }
        )
    }

    fun deleteAllocation(id: Int) {
        val alertQuestionDialog = AlertDialog.Builder(this)
        alertQuestionDialog.setTitle("Confirm deletion")
        alertQuestionDialog.setMessage("Are you sure you want to delete this item?")

        val alertConfirmationDialog = AlertDialog.Builder(this)
        alertConfirmationDialog.setTitle("Confirmed deletion")
        alertConfirmationDialog.setMessage("Deleted item")

        alertQuestionDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        alertQuestionDialog.setPositiveButton("Confirm") { dialog, which ->
            repository.deleteAllocation(id, {}, {})
            dialog.dismiss()
            alertConfirmationDialog.show()
        }

        alertConfirmationDialog.setPositiveButton("Ok") { dialog , which ->
            // update list
            getAllocations()
            dialog.dismiss()
        }

        alertQuestionDialog.show()
    }
}
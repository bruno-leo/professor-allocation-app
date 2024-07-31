package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.model.Department
import com.example.professorallocation.repository.DepartmentRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.example.professorallocation.adapter.DeparmentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DepartmentActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: DeparmentAdapter
    private lateinit var repository: DepartmentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = DepartmentRepository(RetrofitConfig.departmentService)
        rv = findViewById(R.id.rvDepartmentList)
        adapter = DeparmentAdapter(
            onEdit = { id, department ->
                updateDepartment(id, department)
            },
            onDelete = { id ->
                deleteDeparment(id)
            }
        )
        rv.adapter = adapter

        getDepartments()
        addDepartment()
    }

    fun addDepartment() {
        val btAddDepartment = findViewById<FloatingActionButton>(R.id.btAddDepartment)
        btAddDepartment.setOnClickListener {
            addDepartmentDialog()
        }
    }

    fun addDepartmentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_department, null)
        val editText = dialogView.findViewById<EditText>(R.id.etNameDepartment)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewDepartment)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewDepartment)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            val inputText = editText.text.toString()
            val newDepartment = Department(
                id = null,
                name = inputText
            )
            saveDepartment(newDepartment)
            alertDialog.dismiss()
            // TODO recarregar lista
        }

        alertDialog.show()
    }

    fun getDepartments() {
        return repository.getDepartments(
            onCall = { departments ->
                Log.i(">>>", "success get departments")
                departments?.let { adapter.addDepartments(it) }
            },
            onError = {
                Log.e(">>>", "error get departments $it")
            }
        )
    }

    fun saveDepartment(department: Department) {
        repository.saveDepartment(
            department,
            {},
            {}
        )
    }

    fun updateDepartment(id: Int, department: Department) {
        repository.updateDepartment(
            id,
            department,
            {},
            {}
        )
    }

    fun deleteDeparment(id: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm deletion")
        alertDialog.setMessage("Are you sure you want to delete this item?")

        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        alertDialog.setPositiveButton("Confirm") { dialog, which ->
            repository.deleteDepartment(id, {}, {})
        }

        alertDialog.show()
    }
}
package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.adapter.DepartmentSpinnerAdapter
import com.example.professorallocation.adapter.ProfessorAdapter
import com.example.professorallocation.model.Department
import com.example.professorallocation.model.Professor
import com.example.professorallocation.repository.DepartmentRepository
import com.example.professorallocation.repository.ProfessorRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfessorActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProfessorAdapter
    private lateinit var repository: ProfessorRepository
    private lateinit var departmentRepository: DepartmentRepository
    private var departmentsList: List<Department> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = ProfessorRepository(RetrofitConfig.professorService)
        departmentRepository = DepartmentRepository(RetrofitConfig.departmentService)
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

        getProfessors()
        addProfessor()
    }

    fun addProfessor() {
        val btAddProfessor = findViewById<FloatingActionButton>(R.id.btAddProfessor)
        btAddProfessor.setOnClickListener {
            addProfessorDialog()
        }
    }

    fun addProfessorDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_professor, null)
        val etName = dialogView.findViewById<EditText>(R.id.etProfessorName)
        val etCpf = dialogView.findViewById<EditText>(R.id.etProfessorCpf)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewProfessor)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewProfessor)
        val spinner = dialogView.findViewById<Spinner>(R.id.spDepartmentList)

        getDepartmentsForSpinner()
        val adapter = DepartmentSpinnerAdapter(this, departmentsList)
        spinner.adapter = adapter
        setupSpinner(spinner, departmentsList)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            val name = etName.text.toString()
            val cpf = etCpf.text.toString()
            val selectedDepartment = spinner.selectedItem as Department
            val newProfessor = Professor(
                id = null,
                name = name,
                cpf = cpf,
                department = selectedDepartment
            )
            saveProfessor(newProfessor)
            alertDialog.dismiss()
            // TODO recarregar lista
        }

        alertDialog.show()
    }

    fun setupSpinner(spinner: Spinner, departmentsList: List<Department>) {
//        val adapter = DepartmentSpinnerAdapter(this, departmentsList)
//        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedDepartment = parent.getItemAtPosition(position) as Department
                Log.i(">>>", "department selected: id ${selectedDepartment.id}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    fun getProfessors() {
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

    fun saveProfessor(professor: Professor) {
        repository.saveProfessor(professor, {}, {})
    }

    fun updateProfessor(id: Int, professor: Professor) {
        //TODO implementar layout
        repository.updateProfessor(id, professor, {}, {})
    }

    fun deleteProfessor(id: Int) {
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
            repository.deleteProfessor(id, {}, {})
            dialog.dismiss()
            alertConfirmationDialog.show()
        }

        alertConfirmationDialog.setPositiveButton("Ok") { dialog , which ->
            dialog.dismiss()
            // TODO recarregar lista
        }

        alertQuestionDialog.show()
    }

    fun getDepartmentsForSpinner() {
        departmentRepository.getDepartments(
            onCall = { departments ->
                Log.i(">>>", "success get departments")
                departments?.let { departmentsList = it }
            },
            onError = {
                Log.e(">>>", "error get departments $it")
            }
        )
    }
}
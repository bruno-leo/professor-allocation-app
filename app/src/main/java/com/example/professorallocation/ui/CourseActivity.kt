package com.example.professorallocation.ui

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.adapter.CourseAdapter
import com.example.professorallocation.model.Course
import com.example.professorallocation.repository.CourseRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CourseActivity : MainActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: CourseAdapter
    private lateinit var repository: CourseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = CourseRepository(RetrofitConfig.courseService)
        rv = findViewById(R.id.rvCourseList)
        adapter = CourseAdapter(
            onEdit = { id, course ->
                updateCourse(id, course)
            },
            onDelete = { id ->
                deleteCourse(id)
            }
        )
        rv.adapter = adapter

        getCourses()
        addCourse()
    }

    fun addCourse() {
        val btAddCourse = findViewById<FloatingActionButton>(R.id.btAddCourse)
        btAddCourse.setOnClickListener {
            addCourseDialog()
        }
    }

    fun addCourseDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_course, null)
        val etName = dialogView.findViewById<EditText>(R.id.etNameCourse)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewCourse)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewCourse)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            val inputText = etName.text.toString()
            val newCourse = Course(
                id = null,
                name = inputText
            )
            saveCourse(newCourse)
            alertDialog.dismiss()
            // update list
            getCourses()
        }

        alertDialog.show()
    }

    fun getCourses() {
        repository.getCourses(
            onCall = { list ->
                Log.i(">>>", "success get courses")
                list?.let { adapter.addCourses(it) }
            },
            onError = {
                Log.e(">>>", "error get courses $it")
            }
        )
    }

    fun saveCourse(course: Course) = repository.saveCourse(course, {}, {})

    fun updateCourse(id: Int, course: Course) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_course, null)
        val tvLabel = dialogView.findViewById<TextView>(R.id.tvLabelCourse)
        val editText = dialogView.findViewById<EditText>(R.id.etNameCourse)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewCourse)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewCourse)
        var newName: String?

        tvLabel.text = "Update the course"
        buttonConfirm.text = "Update"
        editText.setText(course.name)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            newName = editText.text.toString()
            course.let { up ->
                up.id = id
                up.name = newName
            }

            repository.updateCourse(id, course, {
                adapter.updateCourse(id, course)
            }, {})
        }

        alertDialog.show()
    }

    fun deleteCourse(id: Int) {
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
            repository.deleteCourse(id, {}, {})
            dialog.dismiss()
            alertConfirmationDialog.show()
        }

        alertConfirmationDialog.setPositiveButton("Ok") { dialog , which ->
            // update list
            getCourses()
            dialog.dismiss()
        }

        alertQuestionDialog.show()
    }
}
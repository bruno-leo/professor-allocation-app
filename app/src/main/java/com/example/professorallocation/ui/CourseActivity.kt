package com.example.professorallocation.ui

import android.os.Bundle
import android.text.Editable
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
import com.example.professorallocation.model.Course
import com.example.professorallocation.repository.CourseRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.example.professorallocation.adapter.CourseAdapter
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
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null)
        val editText = dialogView.findViewById<EditText>(R.id.etNameCourse)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewCourse)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewCourse)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            val inputText = editText.text.toString()
            val newCourse = Course(
                id = null,
                name = inputText
            )
            saveCourse(newCourse)
            alertDialog.dismiss()
            // TODO recarregar lista
        }

        alertDialog.show()
    }

    fun getCourses() {
        var courses: List<Course>? = emptyList()
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
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null)
        val tvLabel = dialogView.findViewById<TextView>(R.id.tvLabelCourse)
        val editText = dialogView.findViewById<EditText>(R.id.etNameCourse)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewCourse)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewCourse)
        var savedName: String? = null

        tvLabel.text = "Update the course"
        buttonConfirm.text = "Update"
        editText.setText(course.name)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                savedName = s.toString()
            }
        })

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            var updatedCourse = course
            updatedCourse.let { up ->
                up.id = id
                up.name = savedName
            }

            repository.updateCourse(id, updatedCourse, {}, {})
        }
    }

    fun deleteCourse(id: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Confirm deletion")
        alertDialog.setMessage("Are you sure you want to delete this item?")

        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        alertDialog.setPositiveButton("Confirm") { dialog, which ->
            repository.deleteCourse(id, {}, {})
            dialog.dismiss()
        }

        alertDialog.show()
    }
}
package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.model.Course
import com.example.professorallocation.repository.CourseRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.example.professorallocation.utils.CourseAdapter
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

        var courses: List<Course>? = emptyList()
        repository.getCourses(
            onCall = { list ->
                Log.i(">>>", "success get courses")
                courses = list
            },
            onError = {
                Log.e(">>>", "error get courses $it")
            }
        )
        courses?.let { adapter.addCourses(it) }

//        getCourses()
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
        val editText = dialogView.findViewById<EditText>(R.id.editTextCourse)
        val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancelDiaologCourse)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.buttonConfirmDiaologCourse)

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
            saveCourse(repository, newCourse)
            alertDialog.dismiss()
            // TODO recarregar lista
        }

        alertDialog.show()
    }

    /*fun getCourses() {
        var courses: List<Course>? = emptyList()
        repository.getCourses(
            onCall = { list ->
                Log.i(">>>", "success get courses")
                courses = list
            },
            onError = {
                Log.e(">>>", "error get courses $it")
            }
        )

        courses?.let { adapter.addCourses(it) }
    }*/

    fun saveCourse(repository: CourseRepository, course: Course) {
        repository.saveCourse(
            course,
            {},
            {}
        )
    }

    fun updateCourse(id: Int, course: Course) {
        repository.updateCourse(
            id,
            course,
            {},
            {}
        )
    }

    fun deleteCourse(id: Int) {
        repository.deleteCourse(
            id,
            {},
            {}
        )
    }
}
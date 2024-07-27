package com.example.professorallocation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.repository.CourseRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.example.professorallocation.utils.CustomAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CourseActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val courseRepository = CourseRepository(RetrofitConfig.courseService)

        getCourses(courseRepository)
        addCourse(courseRepository)
    }

    private fun addCourse(repository: CourseRepository) {
        val btAddCourse = findViewById<FloatingActionButton>(R.id.btAddCourse)
        btAddCourse.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Modal Title")
            builder.setMessage("This is a modal dialog.")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
    }

    private fun getCourses(repository: CourseRepository) {
        return repository.getCourses(
            onCall = { courses ->
                Log.i(">>>", "success get courses")
                val rvList = findViewById<RecyclerView>(R.id.rvCourseList)
                rvList.adapter = courses?.map { it.name }?.let { CustomAdapter(it) }
            },
            onError = {
                Log.e(">>>", "error get courses $it")
            }
        )
    }

    fun getCourseById(repository: CourseRepository, field: String) {
        // mudar de String para Int / ver aula 4 pt 3 , na ultima hora


        val id = field.toString().toInt()

        repository.getCourseById(
            id,
            {},
            {}
        )
    }

    /*fun saveCourse(repository: CourseRepository, course: Course) {
        repository.saveCourse(
            course,
            {},
            {}
        )
    }*/

    /*fun updateCourse(repository: CourseRepository, id: String, course: Course) {
        // mudar de String para Int / ver aula 4 pt 3 , na ultima hora

        repository.updateCourse(
            id,
            course,
            {},
            {}
        )
    }*/

    /*fun deleteCourse(repository: CourseRepository, id: String) {
        // mudar de String para Int / ver aula 4 pt 3 , na ultima hora
        // consultar para saber se o curso existe
        // se sim -> excluir
        // se nao -> msg erro


    }*/
}
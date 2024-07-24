package com.example.professorallocation.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.professorallocation.R

open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainToolbar = findViewById<Toolbar>(R.id.mainToolbar)
        mainToolbar.title = ""
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_home -> {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.menu_allocation -> {
//            Toast.makeText(this, "Allocation 1", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AllocationActivity::class.java))
            true
        }
        R.id.menu_course -> {
//            Toast.makeText(this, "Course 2", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CourseActivity::class.java))
            true
        }
        R.id.menu_department -> {
//            Toast.makeText(this, "Department 2", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DepartmentActivity::class.java))
            true
        }
        R.id.menu_professor -> {
//            Toast.makeText(this, "Professor 2", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfessorActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }



}
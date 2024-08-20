package com.example.professorallocation.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.adapter.AllocationAdapter
import com.example.professorallocation.adapter.spinner.AllocationCourseSpinnerAdapter
import com.example.professorallocation.adapter.spinner.AllocationDayOfWeekSpinnerAdapter
import com.example.professorallocation.adapter.spinner.AllocationProfessorSpinnerAdapter
import com.example.professorallocation.dto.AllocationDto
import com.example.professorallocation.model.Allocation
import com.example.professorallocation.model.Course
import com.example.professorallocation.model.Professor
import com.example.professorallocation.repository.AllocationRepository
import com.example.professorallocation.repository.CourseRepository
import com.example.professorallocation.repository.ProfessorRepository
import com.example.professorallocation.repository.RetrofitConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Time
import java.time.DayOfWeek
import java.util.Calendar
import java.util.Locale

class AllocationActivity : MainActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: AllocationAdapter
    private lateinit var repository: AllocationRepository
    private lateinit var professorRepository: ProfessorRepository
    private lateinit var courseRepository: CourseRepository
    private var professorList: List<Professor> = emptyList()
    private var courseList: List<Course> = emptyList()

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
                updateAllocation(id, allocation)
            },
            onDelete = { id ->
                deleteAllocation(id)
            }
        )
        rv.adapter = adapter

        getAllocations()
        addAllocation()
    }

    fun addAllocation() {
        getProfessorForSpinner()
        getCoursesForSpinner()

        val btAddAllocation = findViewById<FloatingActionButton>(R.id.btAddAllocation)
        btAddAllocation.setOnClickListener {
            addAllocationDialog()
        }
    }

    fun addAllocationDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_allocation, null)
        val professorAdapter = AllocationProfessorSpinnerAdapter(this, professorList)
        val courseAdapter = AllocationCourseSpinnerAdapter(this, courseList)
        val daysOfWeek = DayOfWeek.entries.map { it.name.uppercase() }
        val daysOfWeekAdapter = AllocationDayOfWeekSpinnerAdapter(this, daysOfWeek)
        val professorSpinner = dialogView.findViewById<Spinner>(R.id.spAllocationProfessor)
        val courseSpinner = dialogView.findViewById<Spinner>(R.id.spAllocationCourse)
        val daySpinner = dialogView.findViewById<Spinner>(R.id.spDaysOfWeek)
        professorSpinner.adapter = professorAdapter
        courseSpinner.adapter = courseAdapter
        daySpinner.adapter = daysOfWeekAdapter
        setupSpinner(professorSpinner, professorList)
        setupSpinner(courseSpinner, courseList)
        setupSpinnerDaysOfWeek(daySpinner)

        val etStart = dialogView.findViewById<EditText>(R.id.etAllocationStartHour)
        val etEnd = dialogView.findViewById<EditText>(R.id.etAllocationEndHour)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewAllocation)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewAllocation)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        etStart.setOnClickListener {
            showTimePickerDialog(etStart)
        }

        etEnd.setOnClickListener {
            showTimePickerDialog(etEnd)
        }

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            val selectedProfessor = professorSpinner.selectedItem as Professor
            val selectedCourse = courseSpinner.selectedItem as Course
            val selectedDayOfWeek = daySpinner.selectedItem as String
            val selectedStartHour = etStart.text.toString().let { convertToTime(it) }
            val selectedEndHour = etEnd.text.toString().let { convertToTime(it) }

            val newAllocation = AllocationDto(
                day = selectedDayOfWeek,
                startHour = selectedStartHour,
                endHour = selectedEndHour,
                professorId = selectedProfessor.id,
                courseId = selectedCourse.id
            )
            saveAllocation(newAllocation)
            alertDialog.dismiss()
            // update list
            getAllocations()
        }

        alertDialog.show()
    }

    private fun convertToTime(time: String): String? {
        val parts = time.split(":")
        return if (parts.size == 2) {
            val hour = parts[0].toIntOrNull()
            val minute = parts[1].toIntOrNull()

            if (hour != null && minute != null) {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                Time(calendar.timeInMillis).toString()
            } else {
                null
            }
        } else {
            null
        }
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                editText.setText(formattedTime)
            },
            hour, minute, true
        )

        timePickerDialog.show()
    }

    fun setupSpinner(spinner: Spinner, list: List<Any>) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as Any
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun setupCourseSpinner() {

    }

    fun setupSpinnerDaysOfWeek(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectItem = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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

    fun saveAllocation(allocation: AllocationDto) {
        repository.saveAllocation(allocation, {}, {})
    }

    fun updateAllocation(id: Int, allocation: Allocation) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_allocation, null)
        val professorAdapter = AllocationProfessorSpinnerAdapter(this, professorList)
        val courseAdapter = AllocationCourseSpinnerAdapter(this, courseList)
        val daysOfWeek = DayOfWeek.entries.map { it.name.uppercase() }
        val daysOfWeekAdapter = AllocationDayOfWeekSpinnerAdapter(this, daysOfWeek)
        val professorSpinner = dialogView.findViewById<Spinner>(R.id.spAllocationProfessor)
        val courseSpinner = dialogView.findViewById<Spinner>(R.id.spAllocationCourse)
        val daySpinner = dialogView.findViewById<Spinner>(R.id.spDaysOfWeek)
        professorSpinner.adapter = professorAdapter
        courseSpinner.adapter = courseAdapter
        daySpinner.adapter = daysOfWeekAdapter
        setupSpinner(professorSpinner, professorList)
        setupSpinner(courseSpinner, courseList)
        setupSpinnerDaysOfWeek(daySpinner)

        val tvLabel = dialogView.findViewById<TextView>(R.id.tvLabelAllocation)
        val etStart = dialogView.findViewById<EditText>(R.id.etAllocationStartHour)
        val etEnd = dialogView.findViewById<EditText>(R.id.etAllocationEndHour)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.btConfirmNewAllocation)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btCancelNewAllocation)

        tvLabel.text = "Update the Allocation"
        buttonConfirm.text = "Update"
        etStart.setText(allocation.startHour)
        etEnd.setText(allocation.endHour)
        val positionProfessor = professorList.indexOf(allocation.professor)
        professorSpinner.let {
            if (positionProfessor != -1) it.setSelection(positionProfessor)
        }
        val coursePosition = courseList.indexOf(allocation.course)
        courseSpinner.let {
            if (coursePosition != -1) it.setSelection(coursePosition)
        }

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        etStart.setOnClickListener {
            showTimePickerDialog(etStart)
        }

        etEnd.setOnClickListener {
            showTimePickerDialog(etEnd)
        }

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonConfirm.setOnClickListener {
            val selectedProfessor = professorSpinner.selectedItem as Professor
            val selectedCourse = courseSpinner.selectedItem as Course
            val selectedDayOfWeek = daySpinner.selectedItem as DayOfWeek
            val selectedStartHour = etStart.text.toString().let { convertToTime(it) }
            val selectedEndHour = etEnd.text.toString().let { convertToTime(it) }
            val allocation = Allocation(
                null,
                day = selectedDayOfWeek,
                startHour = selectedStartHour,
                endHour = selectedEndHour,
                selectedProfessor,
                selectedCourse
            )

            repository.updateAllocation(id, allocation, {
                adapter.updateAllocations(id, allocation)
            }, {})
            alertDialog.dismiss()
        }

        alertDialog.show()
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

    private fun getProfessorForSpinner() {
        professorRepository.getProfessors(
            onCall = { professors ->
                Log.i(">>>", "success get professors for spinner")
                professors?.let { professorList = it }
            },
            onError = {
                Log.e(">>>", "error get professors for spinner $it")
            }
        )
    }

    private fun getCoursesForSpinner() {
        courseRepository.getCourses(
            onCall = { courses ->
                Log.i(">>>", "success get courses for spinner")
                courses?.let { courseList = it }
            },
            onError = {
                Log.e(">>>", "error get courses for spinner $it")
            }
        )
    }
}

package com.example.retrofit.model

import com.example.professorallocation.model.Course
import java.sql.Time
import java.time.DayOfWeek

data class Allocation(
    val id: Int,
    val day: DayOfWeek,
    val startHour: Time,
    val endHour: Time,
    val professor: Professor,
    val course: Course
)

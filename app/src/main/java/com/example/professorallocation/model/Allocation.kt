package com.example.professorallocation.model

import java.time.DayOfWeek

data class Allocation(
    val id: Int?,
    val day: DayOfWeek?,
    val startHour: String?,
    val endHour: String?,
    val professor: Professor?,
    val course: Course?
)

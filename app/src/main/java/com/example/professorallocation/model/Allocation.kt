package com.example.professorallocation.model

import java.time.DayOfWeek

data class Allocation(
    val id: Int?,
    var day: DayOfWeek?,
    var startHour: String?,
    var endHour: String?,
    var professor: Professor?,
    var course: Course?
)

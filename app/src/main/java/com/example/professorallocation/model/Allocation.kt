package com.example.professorallocation.model

data class Allocation(
    val id: Int?,
    val day: String?,
    val startHour: String?,
    val endHour: String?,
    val professor: Professor?,
    val course: Course?
)

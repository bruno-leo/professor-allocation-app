package com.example.professorallocation.dto

data class AllocationDto(
    val day: String?,
    val startHour: String?,
    val endHour: String?,
    val professor: Int?,
    val course: Int?
)

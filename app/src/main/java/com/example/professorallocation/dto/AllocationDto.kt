package com.example.professorallocation.dto

data class AllocationDto(
    val day: String?,
    val startHour: String?,
    val endHour: String?,
    val professorId: Int?,
    val courseId: Int?
)

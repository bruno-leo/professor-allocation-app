package com.example.retrofit.model

data class Professor(
    val id: Int,
    val name: String,
    val cpf: String,
    val department: Department
)
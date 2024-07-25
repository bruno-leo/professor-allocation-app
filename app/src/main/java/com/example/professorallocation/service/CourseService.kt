package com.example.professorallocation.service

import com.example.professorallocation.model.Course
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CourseService {

    @GET("courses")
    fun getAll(): Call<List<Course>>

    @GET("courses/{id}")
    fun getById(@Path("id") id: Int): Call<Course>

    @POST("courses")
    fun save(@Body course: Course): Call<Any>

    @PUT("courses/{id}")
    fun update(@Path("id") id: Int, @Body course: Course): Call<Course>

    @DELETE("courses/{id}")
    fun delete(@Path("id") id: Int): Call<Any>
}
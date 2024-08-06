package com.example.professorallocation.repository

import com.example.professorallocation.model.Course
import com.example.professorallocation.service.CourseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseRepository(
    private val courseService: CourseService
) {
    fun saveCourse(
        course: Course,
        onCall: () -> Unit,
        onError: () -> Unit
    ) {
        courseService.save(course).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, response: Response<Any>) {
                onCall()
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError()
            }
        })
    }

    fun getCourses(
        onCall: (courses: List<Course>?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        courseService.getAll().enqueue(object : Callback<List<Course>> {
            override fun onResponse(p0: Call<List<Course>>, response: Response<List<Course>>) {
                response.isSuccessful.let {
                    if (it) onCall(response.body())
                    else onError(response.message())
                }
            }

            override fun onFailure(p0: Call<List<Course>>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }

    fun getCourseById(
        id: Int,
        onCall: (courses: Course?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        courseService.getById(id).enqueue(object : Callback<Course> {
            override fun onResponse(p0: Call<Course>, response: Response<Course>) {
                response.isSuccessful.let {
                    if (it) onCall(response.body())
                    else onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Course>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }

    fun updateCourse(
        id: Int,
        course: Course,
        onCall: (courses: Course?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        courseService.update(id, course).enqueue(object : Callback<Course> {
            override fun onResponse(p0: Call<Course>, response: Response<Course>) {
                response.isSuccessful.let {
                    if (it) onCall(response.body())
                    else onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Course>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }

    fun deleteCourse(
        id: Int,
        onCall: () -> Unit,
        onError: (message: String) -> Unit
    ) {
        courseService.delete(id).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, response: Response<Any>) {
                response.isSuccessful.let {
                    if (it) onCall()
                    else onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }
}
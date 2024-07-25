package com.example.professorallocation.repository

import com.example.professorallocation.model.Professor
import com.example.professorallocation.service.ProfessorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfessorRepository(
    private val professorService: ProfessorService
) {
    fun saveProfessor(
        professor: Professor,
        onCall: () -> Unit,
        onError: () -> Unit
    ) {
        professorService.save(professor).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                onCall()
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError()
            }
        })
    }

    fun getProfessors(
        onCall: (professors: List<Professor>?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        professorService.getAll().enqueue(object : Callback<List<Professor>> {
            override fun onResponse(p0: Call<List<Professor>>, response: Response<List<Professor>>) {
                response.isSuccessful.let {
                    if (it)
                        onCall(response.body())
                    else
                        onError(response.message())
                }
            }

            override fun onFailure(p0: Call<List<Professor>>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }

    fun getProfessorById(
        id: Int,
        onCall: (professor: Professor?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        professorService.getById(id).enqueue(object : Callback<Professor> {
            override fun onResponse(p0: Call<Professor>, response: Response<Professor>) {
                response.isSuccessful.let {
                    if (it)
                        onCall(response.body())
                    else
                        onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Professor>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }

    fun updateProfessor(
        id: Int,
        professor: Professor,
        onCall: (professor: Professor?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        professorService.update(id, professor).enqueue(object : Callback<Professor> {
            override fun onResponse(p0: Call<Professor>, response: Response<Professor>) {
                response.isSuccessful.let {
                    if (it)
                        onCall(response.body())
                    else
                        onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Professor>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }

    fun deleteProfessor(
        id: Int,
        onCall: () -> Unit,
        onError: (message: String) -> Unit
    ) {
        professorService.delete(id).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                onCall()
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }
        })
    }
}
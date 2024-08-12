package com.example.professorallocation.repository

import com.example.professorallocation.dto.AllocationDto
import com.example.professorallocation.model.Allocation
import com.example.professorallocation.service.AllocationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllocationRepository(
    private val allocationService: AllocationService
) {
    fun saveAllocation(
        allocation: AllocationDto,
        onCall: () -> Unit,
        onError: () -> Unit
    ) {
        allocationService.save(allocation).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, response: Response<Any>) {
                onCall()
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError()
            }

        })
    }

    fun getAllocations(
        onCall: (allocations: List<Allocation>?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        allocationService.getAll().enqueue(object : Callback<List<Allocation>> {
            override fun onResponse(p0: Call<List<Allocation>>, response: Response<List<Allocation>>) {
                response.isSuccessful.let {
                    if (it) onCall(response.body())
                    else onError(response.message())
                }
            }

            override fun onFailure(p0: Call<List<Allocation>>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }

        })
    }

    fun getAllocationById(
        id: Int,
        onCall: (allocations: Allocation?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        allocationService.getById(id).enqueue(object : Callback<Allocation> {
            override fun onResponse(p0: Call<Allocation>, response: Response<Allocation>) {
                response.isSuccessful.let {
                    if (it) onCall(response.body())
                    else onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Allocation>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }

        })
    }

    fun updateAllocation(
        id: Int,
        allocation: Allocation,
        onCall: (allocations: Allocation?) -> Unit,
        onError: (message: String) -> Unit
    ) {
        allocationService.update(id, allocation).enqueue(object : Callback<Allocation> {
            override fun onResponse(p0: Call<Allocation>, response: Response<Allocation>) {
                response.isSuccessful.let {
                    if (it) onCall(response.body())
                    else onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Allocation>, p1: Throwable) {
                p1.message?.let { onError(it) }
            }

        })
    }

    fun deleteAllocation(
        id: Int,
        onCall: () -> Unit,
        onError: (message: String) -> Unit
    ) {
        allocationService.delete(id).enqueue(object : Callback<Any> {
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
package com.example.employeeapp.network

import com.example.employeeapp.EmployeeDetailResponse
import com.example.employeeapp.model.EmployeeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // Call API url = BASE_URL + "/employees"
    @GET("employees")
    fun getAllEmployee(): Call<EmployeeResponse>

    @GET("employee/{id}")
    fun getEmployeeDetail(
        @Path("id") id: Int
    ): Call<EmployeeDetailResponse>
}
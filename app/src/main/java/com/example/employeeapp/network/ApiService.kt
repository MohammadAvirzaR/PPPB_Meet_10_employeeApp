package com.example.employeeapp.network

import com.example.employeeapp.DeleteResponse
import com.example.employeeapp.EmployeeDetailResponse
import com.example.employeeapp.model.CreateEmployee
import com.example.employeeapp.model.CreateResponse
import com.example.employeeapp.model.EmployeeResponse
import com.example.employeeapp.model.UpdateEmployee
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    // Call API url = BASE_URL + "/employees"
    @GET("employees")
    fun getAllEmployee(): Call<EmployeeResponse>

    @GET("employee/{id}")
    fun getEmployeeDetail(
        @Path("id") id: Int
    ): Call<EmployeeDetailResponse>
    @POST("create")
    fun createEmployee(
        @Body body: CreateEmployee
    ):Call<CreateResponse>

    @PUT("update/{id}")
    fun updateEmployee(
        @Path("id") id: Int,
        @Body body: UpdateEmployee
    ): Call<CreateResponse>
    @DELETE("delete/{id}")
    fun deleteEmployee(
        @Path("id") id: Int
    ): Call<DeleteResponse>

}
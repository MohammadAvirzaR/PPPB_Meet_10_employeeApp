package com.example.employeeapp
import com.example.employeeapp.model.Employee
import com.example.employeeapp.model.EmployeeResponse
import com.google.gson.annotations.SerializedName

data class EmployeeDetailResponse(
    @SerializedName("data")
    val data: Employee
)

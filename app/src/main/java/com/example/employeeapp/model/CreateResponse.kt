package com.example.employeeapp.model

import android.provider.ContactsContract

data class CreateResponse(
    val status: String,
    val data : ContactsContract.Data
)

package com.example.employeeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.employeeapp.databinding.ActivityCreateUpdateEmployeeBinding
import com.example.employeeapp.model.CreateEmployee
import com.example.employeeapp.model.UpdateEmployee
import com.example.employeeapp.model.CreateResponse
import com.example.employeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateUpdateEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateUpdateEmployeeBinding
    private val api = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUpdateEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isUpdate = intent.getBooleanExtra("IS_UPDATE", false)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (isUpdate) {
            binding.btnSubmit.text = "Update Employee"
        }

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val salary = binding.etSalary.text.toString()
            val age = binding.etAge.text.toString()

            if (isUpdate) {
                updateEmployee(id, name, salary, age)
            } else {
                createEmployee(name, salary, age)
            }
        }
    }

    private fun createEmployee(name: String, salary: String, age: String) {
        val body = CreateEmployee(name, salary, age)

        api.createEmployee(body).enqueue(object : Callback<CreateResponse> {

            override fun onResponse(
                call: Call<CreateResponse>,
                response: Response<CreateResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CreateUpdateEmployeeActivity,
                        "Berhasil dibuat",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@CreateUpdateEmployeeActivity,
                        "Gagal: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                Toast.makeText(
                    this@CreateUpdateEmployeeActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateEmployee(id: Int, name: String, salary: String, age: String) {
        val body = UpdateEmployee(name, salary, age)

        api.updateEmployee(id, body).enqueue(object : Callback<CreateResponse> {

            override fun onResponse(
                call: Call<CreateResponse>,
                response: Response<CreateResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CreateUpdateEmployeeActivity,
                        "Berhasil diupdate",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@CreateUpdateEmployeeActivity,
                        "Gagal update: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                Toast.makeText(
                    this@CreateUpdateEmployeeActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

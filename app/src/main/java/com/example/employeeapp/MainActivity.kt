package com.example.employeeapp

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.employeeapp.databinding.ActivityMainBinding
import com.example.employeeapp.model.EmployeeResponse
import com.example.employeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Call API
    private val apiClient = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Panggil API saat Activity dimulai
        getAllEmployee()
    }

    private fun getAllEmployee() {
        val response = apiClient.getAllEmployee()

        response.enqueue(object : Callback<EmployeeResponse?> {
            override fun onResponse(
                call: Call<EmployeeResponse?>,
                response: Response<EmployeeResponse?>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val body = response.body()
                val employees = body?.data.orEmpty()

                if (employees.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "DATA EMPLOYEE KOSONG",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                // Ambil nama semua employee
                val names = employees.map { it.name }

                // ArrayAdapter untuk ListView
                val listAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1, // Layout bawaan untuk ListView
                    names // data employee
                )

                // Set adapter ke ListView
                binding.lvEmployee.adapter = listAdapter

                binding.lvEmployee.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    val intent = Intent(this@MainActivity, DetailEmployeeActivity::class.java)

                    val id = employees[position].id
                    intent.putExtra("EXTRA_ID", id)

                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<EmployeeResponse?>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "GAGAL: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

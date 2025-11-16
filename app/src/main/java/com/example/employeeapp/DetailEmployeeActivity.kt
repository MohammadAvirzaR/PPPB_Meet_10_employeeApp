package com.example.employeeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.employeeapp.databinding.ActivityDetailEmployeeBinding
import com.example.employeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent

class DetailEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEmployeeBinding
    private val apiClient = ApiClient.getInstance() // 🔹 pastikan ApiClient ada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil ID dari Intent
        val employeeId = intent.getIntExtra("EXTRA_ID", -1)

        if (employeeId != -1) {
            getEmployeeDetail(employeeId)
        } else {
            Toast.makeText(this, "ID Employee tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getEmployeeDetail(id: Int) {
        val response = apiClient.getEmployeeDetail(id) // 🔹 panggil method yg benar

        response.enqueue(object : Callback<EmployeeDetailResponse> { // 🔹 enqueue, bukan unqueue
            override fun onResponse(
                call: Call<EmployeeDetailResponse>,
                response: Response<EmployeeDetailResponse>
            ) {
                if (!response.isSuccessful) { // 🔹 isSuccessful typo sebelumnya
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val employee = response.body()?.data
                if (employee == null) {
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "DATA EMPLOYEE KOSONG",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                binding.txtName.setText(employee?.name.toString())
                binding.txtSalary.setText(employee?.salary.toString())
                binding.txtAge.setText(employee?.age.toString())
            }

            override fun onFailure(call: Call<EmployeeDetailResponse>, t: Throwable) {
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    "Gagal memuat data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
        apiClient.deleteEmployee(id).enqueue(object : Callback<DeleteResponse> {
            override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                Toast.makeText(this@DetailEmployeeActivity, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                Toast.makeText(this@DetailEmployeeActivity, "Gagal hapus", Toast.LENGTH_SHORT).show()
            }
        })

    }
}

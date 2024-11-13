package com.example.bai3

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tham chiếu tới TextView để hiển thị số lần mở ứng dụng
        val tvOpenCount: TextView = findViewById(R.id.tvOpenCount)

        // Tạo SharedPreferences
        val sharedPreferences = getSharedPreferences("AppOpenPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Lấy số lần mở hiện tại từ SharedPreferences
        val openCount = sharedPreferences.getInt("openCount", 0)

        // Tăng số lần mở lên 1
        val newOpenCount = openCount + 1
        editor.putInt("openCount", newOpenCount)
        editor.apply()

        // Hiển thị số lần mở ứng dụng
        tvOpenCount.text = "Số lần mở ứng dụng: $newOpenCount"
    }
}

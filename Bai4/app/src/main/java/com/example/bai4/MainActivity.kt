package com.example.bai4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        btnLogout = findViewById(R.id.btnLogout)

        // Khi nhấn "Đăng xuất", xóa trạng thái đăng nhập
        btnLogout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)  // Đặt isLoggedIn thành false
            editor.apply()

            // Chuyển về màn hình đăng nhập
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        // Kiểm tra lại trạng thái đăng nhập khi quay lại màn hình chính
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (!isLoggedIn) {
            // Nếu chưa đăng nhập, chuyển về màn hình đăng nhập
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}


package com.example.bai4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Kiểm tra xem người dùng đã đăng nhập chưa
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            // Nếu đã đăng nhập, chuyển thẳng vào màn hình chính
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            // Kiểm tra thông tin đăng nhập
            if (username == "user" && password == "password") {
                // Lưu trạng thái đăng nhập
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)  // Đánh dấu là đã đăng nhập
                editor.apply()

                // Chuyển đến màn hình chính
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


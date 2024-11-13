package com.example.bai1

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tham chiếu tới các thành phần giao diện
        val editText: EditText = findViewById(R.id.editText)
        val btnSave: Button = findViewById(R.id.btnSave)
        val btnRetrieve: Button = findViewById(R.id.btnRetrieve)

        // Tạo SharedPreferences
        val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)

        // Xử lý lưu dữ liệu
        btnSave.setOnClickListener {
            val inputText = editText.text.toString()
            if (inputText.isNotEmpty()) {
                val editor = sharedPreferences.edit()
                editor.putString("savedText", inputText)
                editor.apply()
                Toast.makeText(this, "Dữ liệu đã được lưu!", Toast.LENGTH_SHORT).show()
                editText.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập dữ liệu!", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý lấy dữ liệu
        btnRetrieve.setOnClickListener {
            val savedText = sharedPreferences.getString("savedText", null)
            if (savedText != null) {
                Toast.makeText(this, "Dữ liệu đã lưu: $savedText", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Không có dữ liệu được lưu!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

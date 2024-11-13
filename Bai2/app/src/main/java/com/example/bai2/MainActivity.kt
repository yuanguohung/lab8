package com.example.bai2

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tham chiếu tới các thành phần giao diện
        val switchDarkMode: Switch = findViewById(R.id.switchDarkMode)
        val radioGroupTextSize: RadioGroup = findViewById(R.id.radioGroupTextSize)
        val radioSmall: RadioButton = findViewById(R.id.radioSmall)
        val radioMedium: RadioButton = findViewById(R.id.radioMedium)
        val radioLarge: RadioButton = findViewById(R.id.radioLarge)

        // Tạo SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Khôi phục cài đặt khi mở ứng dụng
        val isDarkModeEnabled = sharedPreferences.getBoolean("darkMode", false)
        val textSize = sharedPreferences.getString("textSize", "Medium")

        switchDarkMode.isChecked = isDarkModeEnabled
        when (textSize) {
            "Small" -> radioSmall.isChecked = true
            "Medium" -> radioMedium.isChecked = true
            "Large" -> radioLarge.isChecked = true
        }

        // Áp dụng chế độ tối
        if (isDarkModeEnabled) {
            enableDarkMode(true)
        }

        // Xử lý sự kiện khi người dùng thay đổi cài đặt
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("darkMode", isChecked)
            editor.apply()
            enableDarkMode(isChecked)
        }

        radioGroupTextSize.setOnCheckedChangeListener { _, checkedId ->
            val selectedSize = when (checkedId) {
                R.id.radioSmall -> "Small"
                R.id.radioMedium -> "Medium"
                R.id.radioLarge -> "Large"
                else -> "Medium"
            }
            editor.putString("textSize", selectedSize)
            editor.apply()
        }
    }

    // Hàm để áp dụng chế độ tối
    private fun enableDarkMode(enable: Boolean) {
        val nightMode = if (enable) {
            Configuration.UI_MODE_NIGHT_YES
        } else {
            Configuration.UI_MODE_NIGHT_NO
        }
        delegate.localNightMode = nightMode
    }
}

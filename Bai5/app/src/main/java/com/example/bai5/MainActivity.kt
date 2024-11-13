package com.example.bai5
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var taskListView: ListView
    private lateinit var taskAdapter: ArrayAdapter<String>
    private lateinit var taskList: MutableList<Task>
    private lateinit var taskNames: MutableList<String>  // Chứa tên tác vụ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("taskPrefs", MODE_PRIVATE)

        taskListView = findViewById(R.id.taskListView)
        val addButton: Button = findViewById(R.id.addButton)
        val taskEditText: EditText = findViewById(R.id.taskEditText)

        // Đọc danh sách tác vụ từ SharedPreferences
        loadTasks()

        // Thiết lập Adapter cho ListView
        taskAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskNames)
        taskListView.adapter = taskAdapter

        // Thêm tác vụ
        addButton.setOnClickListener {
            val taskName = taskEditText.text.toString()
            if (taskName.isNotEmpty()) {
                addTask(taskName)
                taskEditText.text.clear() // Xóa nội dung sau khi thêm
            }
        }

        // Xử lý sự kiện click vào tác vụ trong danh sách để chỉnh sửa hoặc xóa
        taskListView.setOnItemClickListener { _, _, position, _ ->
            val task = taskList[position]
            showEditDialog(task, position)
        }
    }

    // Hàm để lưu danh sách tác vụ vào SharedPreferences
    private fun saveTasks() {
        val gson = Gson()
        val json = gson.toJson(taskList)
        val editor = sharedPreferences.edit()
        editor.putString("taskList", json)
        editor.apply()
    }

    // Hàm để tải danh sách tác vụ từ SharedPreferences
    private fun loadTasks() {
        val gson = Gson()
        val json = sharedPreferences.getString("taskList", null)
        val type = object : TypeToken<MutableList<Task>>() {}.type
        taskList = if (json != null) {
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }

        taskNames = taskList.map { it.name }.toMutableList() // Lấy tên tác vụ để hiển thị
    }

    // Hàm để thêm tác vụ mới
    private fun addTask(taskName: String) {
        val task = Task(taskList.size + 1, taskName)
        taskList.add(task)
        taskNames.add(task.name)
        saveTasks()
        taskAdapter.notifyDataSetChanged()
    }

    // Hiển thị hộp thoại để chỉnh sửa hoặc xóa tác vụ
    private fun showEditDialog(task: Task, position: Int) {
        val editText = EditText(this)
        editText.setText(task.name)

        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Chỉnh sửa tác vụ")
            .setView(editText)
            .setPositiveButton("Chỉnh sửa") { _, _ ->
                // Thay vì thay đổi trực tiếp task.name, tạo một task mới
                val updatedTask = Task(task.id, editText.text.toString())
                taskList[position] = updatedTask
                taskNames[position] = updatedTask.name
                saveTasks()
                taskAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Xóa") { _, _ ->
                taskList.removeAt(position)
                taskNames.removeAt(position)
                saveTasks()
                taskAdapter.notifyDataSetChanged()
            }
            .setNeutralButton("Hủy", null)
            .create()

        dialog.show()
    }

}

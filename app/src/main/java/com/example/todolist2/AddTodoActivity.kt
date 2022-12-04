package com.example.todolist2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolist2.databinding.ActivityAddTodoBinding
import com.example.todolist2.databinding.ActivityMainBinding
import com.example.todolist2.db.AppDatabase
import com.example.todolist2.db.ToDoDao
import com.example.todolist2.db.ToDoEntity

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddTodoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao : ToDoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnCompletion.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val todoTitle = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId

        when(todoImportance) {
            R.id.btn_high -> {
                todoImportance = 1
            }

            R.id.btn_middle -> {
                todoImportance = 2
            }

            R.id.btn_low -> {
                todoImportance = 3
            }

            else -> {
                todoImportance = -1
            }
        }

        if (todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                todoDao.insertTodo(ToDoEntity(null, todoTitle, todoImportance))
                runOnUiThread {
                    Toast.makeText(this, "추가 되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}
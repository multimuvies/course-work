package com.example.course_work1

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.course_work1.databinding.ActivityAuthorize2Binding

class Authorize2Activity : AppCompatActivity() {
    lateinit var binding: ActivityAuthorize2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorize2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyTgCodeButton.setOnClickListener{
            if(binding.editTgBotCode.text.toString().isEmpty())
            {
                Toast.makeText(this, "Введите код", Toast.LENGTH_SHORT).show()
            } else {

                AutothorizationBot(this).checkCode(binding.editTgBotCode.text.toString().toInt())

                if(AutothorizationBot(this).getStatus() == 1) {
                    UserProfile(this).Authorize(AutothorizationBot(this).getUserName())
                    finish()
                } else {
                    Toast.makeText(this, "Неверный код", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
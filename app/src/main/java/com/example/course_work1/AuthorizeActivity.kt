package com.example.course_work1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.course_work1.databinding.ActivityAuthorizeBinding
import kotlin.system.exitProcess

class AuthorizeActivity : AppCompatActivity() {
    lateinit var binding : ActivityAuthorizeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(AutothorizationBot(this).getStatus() == 1) {
            finish()
        }
        binding.verifyTgButton.setOnClickListener{
            if(binding.editUserName.text.toString().isEmpty())
            {
                Toast.makeText(this, "Укажите ваш никнейм", Toast.LENGTH_SHORT).show()
            } else {
                AutothorizationBot(this).waitUserResponce(binding.editUserName.text.toString())

                val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/find_tutor_bot"))
                telegram.setPackage("org.telegram.messenger")
                startActivity(telegram)
                Thread.sleep(8 * 1000L)

                intent = Intent(this, Authorize2Activity::class.java)
                startActivity(intent)

                if(AutothorizationBot(this).getStatus() == 1) {
                    finish()
                } else {
                    // Toast.makeText(this, "Ошибка верификации", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }
    override fun onResume() {

        super.onResume()
        binding = ActivityAuthorizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(AutothorizationBot(this).getStatus() == 1) {
            finish()
        }
        binding.verifyTgButton.setOnClickListener{
            if(binding.editUserName.text.toString().isEmpty())
            {
                Toast.makeText(this, "Укажите ваш никнейм", Toast.LENGTH_SHORT).show()
            } else {
                AutothorizationBot(this).waitUserResponce(binding.editUserName.text.toString())

                val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/find_tutor_bot"))
                telegram.setPackage("org.telegram.messenger")
                startActivity(telegram)

                Thread.sleep(8 * 1000L)

                intent = Intent(this, Authorize2Activity::class.java)
                startActivity(intent)

                if(AutothorizationBot(this).getStatus() == 1) {
                    finish()
                } else {
                   // Toast.makeText(this, "Ошибка верификации", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
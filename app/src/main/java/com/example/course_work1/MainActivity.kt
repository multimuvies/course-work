package com.example.course_work1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.course_work1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.navBarMain.selectedItemId = R.id.main_menu_nav_but

        setContentView(binding.root)
        if(UserProfile(this).isAuthorized() == false) {
            Authorize()
        }
        binding.navBarMain.setOnNavigationItemSelectedListener {
            if(it.itemId == R.id.main_menu_nav_but)
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else if(it.itemId == R.id.edit_prof_nav_but) {
                val intent = Intent(this, EditProfileActivity::class.java)
                startActivity(intent)
            } else if(it.itemId == R.id.search_tutor_nav_but) {
                val intent = Intent(this, SearchTutorsActivity::class.java)
                startActivity(intent)
            }
            true
        }


    }
    fun Authorize()  {
        val intent = Intent(this, AuthorizeActivity::class.java)
        startActivity(intent)
    }
}
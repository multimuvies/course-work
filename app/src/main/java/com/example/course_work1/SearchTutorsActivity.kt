package com.example.course_work1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.course_work1.databinding.ActivitySearchTutorsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class SearchTutorsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchTutorsBinding
    val adapter = TutorCardAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchTutorsBinding.inflate(layoutInflater)
        binding.navBarSearch.selectedItemId = R.id.search_tutor_nav_but
        filterClass(this).clearFilter()
        setContentView(binding.root)



        binding.navBarSearch.setOnNavigationItemSelectedListener {
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
        binding.clearFilterButon.setOnClickListener{
            filterClass(this).clearFilter();
            init()
        }
        binding.filterButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.filter_dialog, null)
            val universitySpinner: Spinner = dialogView.findViewById(R.id.university_spinner)

            val universities = arrayOf("Любой ВУЗ", "МГУ", "НИУ ВШЭ", "МФТИ")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, universities)
            universitySpinner.adapter = adapter


            AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Выберите фильтры")
                .setPositiveButton("Применить") { dialog, which ->
                    val selectedUniversity = universitySpinner.selectedItem.toString()
                    // Используйте selectedUniversity и selectedAge для фильтрации результатов
                    filterClass(this).setFilter(university = selectedUniversity)
                    init()
                }
                .setNegativeButton("Отмена", null)
                .show()
        }

    }

    override fun onResume() {
        super.onResume()
        binding = ActivitySearchTutorsBinding.inflate(layoutInflater)
        binding.navBarSearch.selectedItemId = R.id.search_tutor_nav_but
        setContentView(binding.root)

        filterClass(this).clearFilter()
        init();




        binding.navBarSearch.setOnNavigationItemSelectedListener {
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
        binding.clearFilterButon.setOnClickListener{
            filterClass(this).clearFilter();
            init()
        }
        binding.filterButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.filter_dialog, null)
            val universitySpinner: Spinner = dialogView.findViewById(R.id.university_spinner)
            val schoolSpinner: Spinner = dialogView.findViewById(R.id.school_spinner)


            val universities = arrayOf("Любой ВУЗ", "МГУ", "НИУ ВШЭ", "МФТИ")
            val school_subjects = arrayOf("Любой предмет", "Математика", "Програмирование", "Физика", "Экономика")

            val adapterUni = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, universities)
            universitySpinner.adapter = adapterUni

            val adapterSch = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, school_subjects)
            schoolSpinner.adapter = adapterSch


            AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Выберите фильтры")
                .setPositiveButton("Применить") { dialog, which ->
                    val selectedUniversity = universitySpinner.selectedItem.toString()
                    // Используйте selectedUniversity и selectedAge для фильтрации результатов
                    filterClass(this).setFilter(university = selectedUniversity)
                    init()
                }
                .setNegativeButton("Отмена", null)
                .show()
        }
    }

    private fun init(){
        binding.apply {
            adapter.clearAll()
            rcView.layoutManager = GridLayoutManager(this@SearchTutorsActivity, 1)
            rcView.adapter = adapter
            val dataBase = FirebaseDatabase.getInstance()
            val usersRef = dataBase.getReference("Users")
            usersRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (userSnapshot in dataSnapshot.children) {
                            val userData = userSnapshot.getValue<Map<String, String>>()
                            val userId = userSnapshot.key
                            val tutor_card = TutorCard(userId!!, userData?.get("name")!! + " " + userData?.get("surname")!!, userData?.get("university")!!)
                            if(filterClass(this@SearchTutorsActivity).compFilterToUser(user_university =  userData?.get("university")!!, username = userId) == true ) {
                                adapter.addCard(tutor_card)
                            }
                        }


                    } else {
                        println("Пользователи не найдены.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Ошибка чтения данных: ${databaseError.message}")
                }
            })

        }
    }
}
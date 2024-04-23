package com.example.course_work1

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class filterClass (private val context : Context){
    fun setFilter(university: String){
        var shared_table_name = "SearchFilterTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().putString("UniversityFilter", university).apply()
    }

    fun clearFilter(){
        var shared_table_name = "SearchFilterTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().clear().commit()
    }

    fun compFilterToUser(user_university: String, username: String) : Boolean{
        var shared_table_name = "SearchFilterTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        // Получение ссылки на базу данных Firebase




        if((table?.getString("UniversityFilter", "Любой ВУЗ")!! == user_university || table?.getString("UniversityFilter", "Любой ВУЗ")!! == "Любой ВУЗ")){
            return true;
        } else {
            return false;
        }
    }
}
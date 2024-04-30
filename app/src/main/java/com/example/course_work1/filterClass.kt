package com.example.course_work1

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import javax.security.auth.Subject

class filterClass (private val context : Context){
    fun setFilter(university: String, course: String, subject: String){
        LocalStorageManager(context).addString("SearchFilterTable", "CourseFilter", course)
        LocalStorageManager(context).addString("SearchFilterTable", "UniversityFilter", university)
        LocalStorageManager(context).addString("SearchFilterTable", "SubjectFilter", subject)
    }

    fun clearFilter(){
       LocalStorageManager(context).clear("SearchFilterTable")
    }

    fun compFilterToUser(user_university: String, username: String, user_course: String, user_subject: String = "Любой предмет") : Boolean{
        var shared_table_name = "SearchFilterTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)

        if((table?.getString("CourseFilter", "Любой курс")!! == user_course || table?.getString("CourseFilter", "Любой курс")!! == "Любой курс")
            && (table?.getString("UniversityFilter", "Любой ВУЗ")!! == user_university || table?.getString("UniversityFilter", "Любой ВУЗ")!! == "Любой ВУЗ")){
            return true;
        } else {
            return false;
        }
    }
}
package com.example.course_work1

import android.content.Context

class UserProfile (private val context : Context){
    fun isAuthorized() : Boolean{
        var shared_table_name = "UserProfile"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        return table?.getBoolean("isAuthorized", false)!!
    }

    fun Authorize(username: String) {
        var shared_table_name = "UserProfile"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().putString("Username", username).apply()
        table.edit().putBoolean("isAuthorized", true).apply()
    }

    fun getUsername() : String{
        var shared_table_name = "UserProfile"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        return table?.getString("Username", "default")!!
    }
}
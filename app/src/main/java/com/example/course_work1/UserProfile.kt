package com.example.course_work1

import android.content.Context

class UserProfile (private val context : Context){
    fun isAuthorized() : Boolean{
        return  LocalStorageManager(context).getBoolean("UserProfile","isAuthorized")
    }

    fun Authorize(username: String) {
        LocalStorageManager(context).addString("UserProfile","Username", username)
        LocalStorageManager(context).addBoolean("UserProfile","isAuthorized", true)
    }

    fun getUsername() : String{
        return LocalStorageManager(context).getString("UserProfile", "Username")
    }
}
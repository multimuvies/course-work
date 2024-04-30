package com.example.course_work1

import android.content.Context

class AutothorizationBot(private val context: Context) {
    fun waitUserResponce(username: String)
    {
        LocalStorageManager(context).addString("AuthorizationTable", "Username", username)
    }

    fun getUserName() : String{
        return LocalStorageManager(context).getString("AuthorizationTable", "Username")
    }

    fun setStatus(status: Int){
        LocalStorageManager(context).addInt("AuthorizationTable", "Status", status)
    }

    fun getStatus() : Int{
        return LocalStorageManager(context).getInt("AuthorizationTable", "Status")
    }

    fun checkCode(enterCode: Int){
        if(enterCode == DatabaseManager(context).getValue_int("UsersPasswords", UserProfile(context).getUsername(), "shortCode"))
        {
            setStatus(1);
        } else {
            setStatus(0);
        }
    }
}
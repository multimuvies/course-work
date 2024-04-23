package com.example.course_work1

import android.content.Context

class autothorizationBot(private val context: Context) {
    fun waitUserResponce(username: String)
    {
        var shared_table_name = "AuthorizationTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().putString("Username", username).apply()
    }

    fun getUserName() : String{
        var shared_table_name = "AuthorizationTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        return table?.getString("Username", "default")!!
    }

    fun setStatus(status: Int){
        var shared_table_name = "AuthorizationTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().putInt("Status", status).apply()
    }

    fun getStatus() : Int{
        var shared_table_name = "AuthorizationTable"
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        return table?.getInt("Status", 0)!!
    }

    fun checkCode(enterCode: Int){
        if(enterCode == 5352) {
            setStatus(1)
        }
        else {
            setStatus(0)
        }
    }
}
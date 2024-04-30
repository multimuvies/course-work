package com.example.course_work1

import android.content.Context

class LocalStorageManager(private val context: Context) {
    fun addString(shared_table_name: String, key: String, value: String){
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().putString(key, value).apply()
    }

    fun getString(shared_table_name: String, key: String) : String{
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        return table?.getString(key, "default")!!
    }

    fun addInt(shared_table_name: String, key: String, value: Int){
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().putInt(key, value).apply()
    }

    fun getInt(shared_table_name: String, key: String) : Int{
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        return table?.getInt(key, 0)!!
    }

    fun addBoolean(shared_table_name: String, key: String, value: Boolean){
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(shared_table_name: String, key: String) : Boolean{
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        return table?.getBoolean(key, false)!!
    }

    fun clear(shared_table_name: String)
    {
        var table = context.getSharedPreferences(shared_table_name, Context.MODE_PRIVATE)
        table.edit().clear().commit()
    }
}
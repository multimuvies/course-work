package com.example.course_work1

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class DatabaseManager(private val context : Context) {
    fun getValue(dir: String, username: String, key: String) : String {
        val dataBase = FirebaseDatabase.getInstance()
        var dataRef = dataBase.getReference(dir).child(username)
        var res = ""
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue<Map<String, String>>()
                    res = userData?.get(key)!!
                } else {
                    //println("Пользователь не найден.")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //println("Ошибка чтения данных: ${databaseError.message}")
            }
        })
        return res
    }
    fun getValue_int(dir: String, username: String, key: String) : Int {
        return getValue(dir, username, key).toInt()
    }

    fun getValue_bool(dir: String,username: String, key: String) : Boolean {
        val dataBase = FirebaseDatabase.getInstance()
        var dataRef = dataBase.getReference().child(dir).child(username)
        var res = false
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue<Map<String, Boolean>>()
                    res = userData?.get(key)!!
                } else {
                    //println("Пользователь не найден.")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //println("Ошибка чтения данных: ${databaseError.message}")
            }
        })
        return res
    }
    fun setValue(dir: String, username: String, userMap: Map<String, String>) {
        val storageDb = FirebaseDatabase.getInstance()
        val root = storageDb.getReference()
        root.child(dir).child(username).setValue(userMap)
    }

    fun setValue_bool(dir: String, username: String, userMap: Map<String, Boolean>) {
        val storageDb = FirebaseDatabase.getInstance()
        val root = storageDb.getReference()
        root.child(dir).child(username).setValue(userMap)
    }

}
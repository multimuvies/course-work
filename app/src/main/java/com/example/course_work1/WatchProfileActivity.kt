package com.example.course_work1

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.course_work1.databinding.ActivityWatchProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class WatchProfileActivity : AppCompatActivity() {
    var username = ""
    lateinit var binding : ActivityWatchProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra("WatchProfileUsername")!!


        binding.BackButton.setOnClickListener{
            finish()
        }
        binding.ShowContactsButton.setOnClickListener{
            binding.ShowContactsButton.setText("Telegram nickname - @$username")
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$username"))
            telegram.setPackage("org.telegram.messenger")
            startActivity(telegram)
        }

        updateInfo()
    }
    fun updateInfo(){
        val imageName = username
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName")
        val localfile = File.createTempFile("tempImage", "jpg");

        val dataBase = FirebaseDatabase.getInstance()
        var dataRef = dataBase.getReference().child("Users").child(username)


        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue<Map<String, String>>()

                    binding.NameView.setText(userData?.get("name"))
                    binding.SurNameView.setText(userData?.get("surname"))
                    binding.UniversityView.setText(userData?.get("university"))
                    binding.ViewBio.setText(userData?.get("bio"))
                } else {
                    //println("Пользователь не найден.")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //println("Ошибка чтения данных: ${databaseError.message}")
            }
        })

        dataRef = dataBase.getReference().child("UsersAbility").child(username)
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue<Map<String, Boolean>>()
                    binding.checkboxMath.isChecked = userData?.get("math")!!;
                    binding.checkboxProgramming.isChecked = userData?.get("prog")!!;
                    binding.checkboxPhysics.isChecked = userData?.get("phys")!!;
                    binding.checkboxEconomics.isChecked = userData?.get("eco")!!;
                } else {
                    //println("Пользователь не найден.")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //println("Ошибка чтения данных: ${databaseError.message}")
            }
        })

        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imageView.setImageBitmap(bitmap)
        }.addOnFailureListener {
            //Toast.makeText(this, "fail to get file", Toast.LENGTH_SHORT).show()
        }


    }
}
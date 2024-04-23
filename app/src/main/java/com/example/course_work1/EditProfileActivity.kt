package com.example.course_work1

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.forEach
import com.example.course_work1.databinding.ActivityEditProfileBinding
import com.google.android.material.chip.Chip
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfileBinding
    lateinit var imageUri: Uri
    val universities = arrayOf("ВУЗ не указан", "МГУ", "НИУ ВШЭ", "МФТИ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBarProfile.selectedItemId = R.id.edit_prof_nav_but

        updateInfo();
        binding.imgPicButton.setOnClickListener{
            selectImage();
        }

        binding.saveDataButton.setOnClickListener{
            saveInfo();
        }


        binding.navBarProfile.setOnNavigationItemSelectedListener {
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


        // Настройка ArrayAdapter
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, universities)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Присоединяем адаптер к Spinner
        val spinner = binding.universitySpinner
        spinner.adapter = spinnerAdapter
        // Установка слушателя на выбор элемента
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //Toast.makeText(this@EditProfileActivity, "Выбран университет: ${universities[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    fun updateInfo(){
        val imageName = UserProfile(this).getUsername()
        val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName")
        val localfile = File.createTempFile("tempImage", "jpg");

        val dataBase = FirebaseDatabase.getInstance()
        var dataRef = dataBase.getReference().child("Users").child(UserProfile(this).getUsername())


        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue<Map<String, String>>()

                    binding.editNameButton.setText(userData?.get("name"))
                    binding.editSurNameButton.setText(userData?.get("surname"))
                    binding.universitySpinner.setSelection(userData?.get("university_id")!!.toInt())
                    binding.editBio.setText(userData?.get("bio"))
                } else {
                    //println("Пользователь не найден.")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //println("Ошибка чтения данных: ${databaseError.message}")
            }
        })

        dataRef = dataBase.getReference().child("UsersAbility").child(UserProfile(this).getUsername())
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue<Map<String, Boolean>>()

                    binding.isTutorSwitch.isChecked = userData?.get("tutor")!!;
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

    private fun saveInfo() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Saving information...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        //val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        //val now = Date()
        val fileName = UserProfile(this).getUsername()

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")


        val storageDb = FirebaseDatabase.getInstance()
        val root = storageDb.getReference()
        var userMap = mapOf(
            "name" to binding.editNameButton.text.toString(),
            "surname" to binding.editSurNameButton.text.toString(),
            "university_id" to binding.universitySpinner.selectedItemId.toString(),
            "university" to binding.universitySpinner.selectedItem.toString(),
            "bio" to binding.editBio.text.toString(),
            )
        root.child("Users").child(UserProfile(this).getUsername()).setValue(userMap)

        var abilityMap = mapOf(
            "tutor" to binding.isTutorSwitch.isChecked,
            "math" to binding.checkboxMath.isChecked,
            "prog" to binding.checkboxProgramming.isChecked,
            "phys" to binding.checkboxPhysics.isChecked,
            "eco" to binding.checkboxEconomics.isChecked
        )
        root.child("UsersAbility").child(UserProfile(this).getUsername()).setValue(abilityMap)
        storageReference.putFile(imageUri).
        addOnSuccessListener {
            //binding.imageView.setImageURI(null)
            Toast.makeText(this, "Succesfully saved", Toast.LENGTH_SHORT).show()
            if(progressDialog.isShowing)
                progressDialog.dismiss()

        }.addOnFailureListener {
            if(progressDialog.isShowing)
                progressDialog.dismiss()
            Toast.makeText(this, "Error while saving", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            binding.imageView.setImageURI(imageUri)
        }
    }
}
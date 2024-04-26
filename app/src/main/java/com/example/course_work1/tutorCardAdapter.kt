package com.example.course_work1

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.course_work1.databinding.TutorCardItemBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.ArrayList

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(getAdapterPosition(), getItemViewType())
    }
    return this
}

class TutorCardAdapter(var context: Context): RecyclerView.Adapter<TutorCardAdapter.TutorCardHolder>() {
    var tutorList = ArrayList<TutorCard>()

    class TutorCardHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = TutorCardItemBinding.bind(item)
        fun bind(card: TutorCard) = with(binding){
            nameTitle.text = card.name
            UniversityTitle.text = card.uniersity
            CourseTitle.text = card.course

            val imageName = card.username
            val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName")
            val localfile = File.createTempFile("tempImage", "jpg");
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                im.setImageBitmap(bitmap)
            }.addOnFailureListener {
                //Toast.makeText(this, "fail to get file", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorCardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tutor_card_item, parent, false)
        return TutorCardHolder(view).listen { pos, type ->
            val item_username = tutorList.get(pos).username
            val intent = Intent(context, WatchProfileActivity::class.java)
            intent.putExtra("WatchProfileUsername", item_username)
            context.startActivity(intent)

        }
    }

    override fun onBindViewHolder(holder: TutorCardHolder, position: Int) {
        holder.bind(tutorList[position])
    }

    override fun getItemCount(): Int {
        return tutorList.size
    }

    fun addCard(card: TutorCard){
        tutorList.add(card)
        notifyDataSetChanged()
    }

    fun clearAll()
    {
        tutorList.clear()
        notifyDataSetChanged()
    }
}
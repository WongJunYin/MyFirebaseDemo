package com.example.myfirebasedemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInsert: Button = findViewById(R.id.btnInsert)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Student")

        btnInsert.setOnClickListener() {

            val id:String = findViewById<TextView>(R.id.tvStudentID).text.toString()
            val name:String = findViewById<TextView>(R.id.tvStudentName).text.toString()
            val programme:String = findViewById<TextView>(R.id.tvProgramme).text.toString()

            myRef.child(id).child("Name").setValue(name)
            myRef.child(id).child("Programme").setValue(programme)
        }

        var getData = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var sbName = StringBuilder();

                for (s in snapshot.children){
                    val studentName = s.child("Name").getValue()
                    sbName.append("${studentName} \n")
                }

                findViewById<TextView>(R.id.tvResult).setText(sbName)
            }

        }

        val btnGet : Button = findViewById(R.id.btnGet)

        btnGet.setOnClickListener(){

            val q : Query = myRef.orderByChild("Programme").equalTo("RRR")
            q.addValueEventListener(getData)
            q.addListenerForSingleValueEvent(getData)
            //myRef.addValueEventListener(getData)
            //myRef.addListenerForSingleValueEvent(getData)
        }
    }
}
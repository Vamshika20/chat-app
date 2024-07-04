package com.example.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth= FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance().getReference()

        userList=ArrayList()
        adapter=UserAdapter(this,userList)


        userRecyclerView = findViewById(R.id.chatRecyclerview)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter


        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
               for(postSnapshot in snapshot.children) {
                   val currentUser = postSnapshot.getValue(UserData::class.java)

                   if(mAuth.currentUser?.uid != mAuth.currentUser?.uid)
                   userList.add(currentUser!!)
               }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){

            mAuth.signOut()
            val intent =Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return false
    }
}
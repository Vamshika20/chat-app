package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtname: EditText
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnsignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtname =findViewById(R.id.edt_name)
        edtemail =findViewById(R.id.edt_email)
        edtpassword =findViewById(R.id.edt_password)
        btnsignup =findViewById(R.id.btnsignup)


        btnsignup.setOnClickListener {
            val name=edtname.text.toString()
            val email =edtemail.text.toString()
            val password = edtpassword.text.toString()

            signup(name,email,password)
        }
    }

    private fun signup(name:String,email:String,password:String){
        //logic for signup function
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java )
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp,"some error occurred",Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun addUserToDatabase(name:String,email:String,uid:String){

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(user(name,email,uid))
    }
}
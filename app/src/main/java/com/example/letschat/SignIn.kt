package com.example.letschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.jar.Attributes.Name

class SignIn : AppCompatActivity() {

    private lateinit var edtname: EditText
    private lateinit var edtemail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var signinbutton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        edtname=findViewById(R.id.edt_name)
        edtemail=findViewById(R.id.edt_email)
        edtpassword=findViewById(R.id.edt_password)
        signinbutton=findViewById(R.id.sign_in_button)
        mAuth=FirebaseAuth.getInstance()

        signinbutton.setOnClickListener{
            val email=edtemail.text.toString()
            val password=edtpassword.text.toString()
            val name=edtname.text.toString()

            signUp(name,email,password)
        }
    }
    private fun signUp(name:String,email: String,password: String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent= Intent(this@SignIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignIn,"Sorry, There is an error",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name:String,email:String,uid:String)
    {
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }

}
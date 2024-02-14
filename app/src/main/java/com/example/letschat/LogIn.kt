package com.example.letschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class LogIn : AppCompatActivity() {
    private lateinit var edtemail:EditText
    private lateinit var edtpassword:EditText
    private lateinit var loginbutton:Button
    private lateinit var signinbutton:Button
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        edtemail=findViewById(R.id.edt_email)
        edtpassword=findViewById(R.id.edt_password)
        loginbutton=findViewById(R.id.log_in_button)
        signinbutton=findViewById(R.id.sign_in_button)
        mAuth=FirebaseAuth.getInstance()

        supportActionBar?.hide()

        signinbutton.setOnClickListener{
            val intent= Intent(this,SignIn::class.java)
            startActivity(intent)
        }
        loginbutton.setOnClickListener{
            val email=edtemail.text.toString()
            val password=edtpassword.text.toString()

            login(email,password)


        }
    }
    private fun login(email:String,password:String)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent= Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn,"Please sign in first",Toast.LENGTH_SHORT).show()
                }
            }
    }
}
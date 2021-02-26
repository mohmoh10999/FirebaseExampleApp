package com.example.firebaseexampleapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.firebaseexampleapp.R
import com.example.firebaseexampleapp.firebase.FireStoreClass
import com.example.firebaseexampleapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setUpActionBar()
        btn_sign_in.setOnClickListener {
            Log.i("SignIn", "start Sign In" )
            signInRegisterUser()
        }
    }
    fun setUpActionBar(){
        setSupportActionBar(toolbar_sign_in_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun signInRegisterUser(){
        val email : String = et_email.text.toString().trim{it<=' '}
        val password : String = et_password.text.toString().trim{it <= ' '}
        if(validateForm(email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                       FireStoreClass().loadUserData(this)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.i("SignIn", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    fun validateForm( email:String,password:String):Boolean{
        return when{
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please Enter an email address")
                return false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please Enter a password")
                return false
            }
            else -> true
        }
    }

    fun signInSuccess(loggInUser: User) {
        hideProgressDialog()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
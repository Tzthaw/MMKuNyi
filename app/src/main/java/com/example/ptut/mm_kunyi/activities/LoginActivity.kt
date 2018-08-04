package com.example.ptut.mm_kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener(this)
        register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.register->{
                startActivity(RegisterActivity.newIntent(applicationContext))
            }
            R.id.loginButton->{
                loginUser()
            }
        }
    }

    private fun loginUser(){
        val email = userLogin.text.toString()
        val password = passwordLogin.text.toString()
        if (!email.isEmpty() && !password.isEmpty()) {
           mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(JobListActivity.newIntent(applicationContext))
                    finish()
                } else {
                    Snackbar.make(loginLayout, "Error Loading", Snackbar.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please fill up the Credentials", Toast.LENGTH_SHORT).show()
        }
    }

}

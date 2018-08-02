package com.example.ptut.mm_kunyi.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ptut.mm_kunyi.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        checkEmpty()

    }

    fun checkEmpty(){
        var user = userName.text.toString()
        var pass = passwordEdit.text.toString()
        var mail = email.text.toString()

        if (user == "") {
            userName.error = "can't be blank"
        } else if (pass == "") {
            passwordEdit.error = "can't be blank"
        } else if (!user.matches("[A-Za-z0-9]+".toRegex())) {
            userName.error = "only alphabet or number allowed"
        } else if (user.length < 5) {
            userName.error = "at least 5 characters long"
        } else if (pass.length < 5) {
            passwordEdit.error = "at least 5 characters long"
        } else {
            val pd = ProgressDialog(applicationContext)
            pd.setMessage("Loading...")
            pd.show()
        }
    }
}



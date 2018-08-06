package com.example.ptut.mm_kunyi.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity
import com.example.ptut.mm_kunyi.utils.AppConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), View.OnClickListener {
    lateinit var mDatabase: DatabaseReference
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerButton.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        mDatabase = FirebaseDatabase.getInstance().reference
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.login->startActivity(LoginActivity.newIntent(applicationContext))
            R.id.registerButton->  registerUser()
        }
    }

    private fun registerUser() {
        var nameTxt = userName.text.toString()
        var passwordTxt = passwordEdit.text.toString()
        var emailTxt = email.text.toString()
        if (!emailTxt.isEmpty() && !passwordTxt.isEmpty() && !nameTxt.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val uid = mFirebaseUser!!.uid
                    val map: HashMap<String, Any> = hashMapOf(
                            "username" to nameTxt,
                            "email" to emailTxt,
                            "password" to passwordTxt
                    )
                    mDatabase.child(AppConstants.KUNYI_USER_DR).child(uid).setValue(map)
                    startActivity(LoginActivity.newIntent(applicationContext))
                } else {
                    Toast.makeText(this, "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }
}



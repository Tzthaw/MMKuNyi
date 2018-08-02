package com.example.ptut.mm_kunyi.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ptut.mm_kunyi.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


//        register.setOnClickListener { startActivity(Intent(this@Login, Register::class.java)) }

        loginButton.setOnClickListener {
            var user = userLogin.text.toString()
            var pass = passwordLogin.text.toString()

            if (user.isEmpty()) {
                userLogin.error = "can't be blank"
            } else if (pass.isEmpty()) {
                userLogin.error = "can't be blank"
            } else {
                val url = "https://myfirebaseauth-8da71.firebaseio.com/messages/users.json"
                val pd = ProgressDialog(applicationContext)
                pd.setMessage("Loading...")
                pd.show()

//                val request = StringRequest(Request.Method.GET, url, object : Response.Listener<String>() {
//                    fun onResponse(s: String) {
//                        if (s == "null") {
//
//                            Toast.makeText(this@Login, "user not found", Toast.LENGTH_LONG).show()
//                        } else {
//                            try {
//                                val obj = JSONObject(s)
//
//                                if (!obj.has(user)) {
//                                    Toast.makeText(this@Login, "user not found", Toast.LENGTH_LONG).show()
//                                } else if (obj.getJSONObject(user).getString("password") == pass) {
//                                    val uriImage = obj.getJSONObject(user).getString("userUri")
//                                    UserDetails.username = user
//                                    UserDetails.password = pass
//                                    UserDetails.userUri = uriImage
//                                    val intent = Intent(this@Login, Users::class.java)
//                                    startActivity(intent)
//
//                                    Toast.makeText(this@Login, uriImage, Toast.LENGTH_LONG).show()
//                                } else {
//                                    Toast.makeText(this@Login, "incorrect password", Toast.LENGTH_LONG).show()
//                                }
//                            } catch (e: JSONException) {
//                                e.printStackTrace()
//                            }
//
//                        }
//
//                        pd.dismiss()
//                    }
//                }, object : Response.ErrorListener() {
//                    fun onErrorResponse(volleyError: VolleyError) {
//                        println("" + volleyError)
//                        pd.dismiss()
//                    }
//                })
//
//                val rQueue = Volley.newRequestQueue(this@Login)
//                rQueue.add(request)
            }
        }

    }

}

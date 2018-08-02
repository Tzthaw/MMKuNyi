package com.example.ptut.mm_kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity

class PostJobActivity:BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PostJobActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)
    }
}
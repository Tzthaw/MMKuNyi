package com.example.ptut.mm_kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.ptut.mm_kunyi.R
import com.example.ptut.mm_kunyi.activities.base.BaseActivity

class CommentActivity:BaseActivity() {

    fun newInstance(context: Context):Intent{
        val intent=Intent(context,CommentActivity::class.java)
        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_comment_layout)
    }
}
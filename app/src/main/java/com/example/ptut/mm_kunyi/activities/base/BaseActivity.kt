package com.example.ptut.mm_kunyi.activities.base

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity

import com.example.ptut.mm_kunyi.utils.Error

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), Observer<Error> {
    override fun onChanged(error: Error?) {

    }
}
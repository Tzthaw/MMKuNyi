package com.example.ptut.mm_kunyi.components.pods

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.ptut.mm_kunyi.deligate.BaseController
import com.example.ptut.mm_kunyi.deligate.UserController
import com.example.ptut.mm_kunyi.deligate.ViewController
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.view_login_pod.view.*

open class LoginViewPod : RelativeLayout {

    private lateinit var mController:UserController

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        googleLogin.setOnClickListener { mController.onTapLogin() }
    }

     fun setController(controller: UserController) {
        mController= controller
    }


}
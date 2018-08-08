package com.example.ptut.mm_kunyi.components.pods

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.ptut.mm_kunyi.deligate.BaseController
import com.example.ptut.mm_kunyi.deligate.UserController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.view_login_pod.view.*
import kotlinx.android.synthetic.main.view_profile_pod.view.*

class UserAccountViewPod : FrameLayout {
    lateinit var mAuth: FirebaseAuth
    var mFirebaseUser: FirebaseUser? = null
    lateinit var loginViewPod: LoginViewPod
    lateinit var profileViewPod: ProfileViewPod

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onFinishInflate() {
        super.onFinishInflate()

        loginViewPod= vpLoginUser as LoginViewPod
        profileViewPod=vpLogoutUser as ProfileViewPod
    }

     fun refreshUserLoginStatus() {
         mAuth = FirebaseAuth.getInstance()
         mFirebaseUser = mAuth.currentUser
         profileViewPod.visibility = if (mFirebaseUser == null) View.GONE else View.VISIBLE
         loginViewPod.visibility = if (mFirebaseUser != null) View.GONE else View.VISIBLE
         if(mFirebaseUser!=null){
             profileViewPod.setData(mFirebaseUser!!)
         }
    }

    fun setUserController(userController: BaseController) {
        profileViewPod.setController(userController as UserController)
        loginViewPod.setController(userController)
    }


}
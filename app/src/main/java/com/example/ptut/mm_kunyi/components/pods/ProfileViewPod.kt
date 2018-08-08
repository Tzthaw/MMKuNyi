package com.example.ptut.mm_kunyi.components.pods

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.ptut.mm_kunyi.deligate.UserController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.view_profile_pod.view.*

open class ProfileViewPod : RelativeLayout {
    private var mController: UserController? = null
    private lateinit var mAuth: FirebaseAuth

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        mAuth = FirebaseAuth.getInstance()
        signOutBtn.setOnClickListener {
                mController!!.onTapSignOut()
        }

    }

    fun setController(controller: UserController) {
        mController = controller
    }

    fun setData(mFirebaseUser: FirebaseUser) {
        Glide.with(this)
                .load(mFirebaseUser.photoUrl)
                .into(seekerProfileUrl)
        seekerUserName.text = mFirebaseUser.displayName
        seekerEmail.text = mFirebaseUser.email
    }

}
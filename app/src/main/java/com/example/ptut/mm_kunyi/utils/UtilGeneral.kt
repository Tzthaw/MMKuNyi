package com.example.ptut.mm_kunyi.utils

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.ptut.mm_kunyi.R

open class UtilGeneral {

    companion object {

        fun showNetworkError(rootLayout: View, context: Context, error: NetworkError) {
            Snackbar.make(rootLayout, error.msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Sorry for not available",null)
                    .setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent)).show()

        }

    }

}
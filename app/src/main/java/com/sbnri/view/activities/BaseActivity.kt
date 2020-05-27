package com.sbnri.view.activities

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sbnri.R

abstract class BaseActivity: AppCompatActivity() {

    private var builder : AlertDialog.Builder? = null
    private var progressDialog : Dialog? = null

    fun showDialog(title : String, msg : String) {
        if (builder == null) {
            builder = AlertDialog.Builder(this)
            builder?.setCancelable(false)
            builder?.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }
            })
        }
        builder?.setTitle(title)
        builder?.setMessage(msg)
        builder?.show()
    }

    fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.setContentView(R.layout.progress_layout)
            progressDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            progressDialog?.setCancelable(false)
        }
        progressDialog?.show()
    }

    fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}
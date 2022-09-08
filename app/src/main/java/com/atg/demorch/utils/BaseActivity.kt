package com.atg.dcard.utils

import android.R
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    abstract fun bindView()
    abstract fun observer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        observer()
    }

    fun showAlert(title: String? = null, message: String) {
        runOnUiThread {
            AlertDialog.Builder(this)
                .setTitle(title ?: resources.getString(com.atg.demorch.R.string.txt_error))
                .setMessage(message)
                .setPositiveButton(
                    R.string.yes
                ) { _, _ ->
                }
                .setIcon(R.drawable.ic_dialog_alert)
                .show()

        }
    }

    private var progress: ProgressDialog? = null

    fun showLoading() {
        runOnUiThread {
            if (progress == null) {
                progress = ProgressDialog(this)
                progress!!.setTitle(resources.getString(com.atg.demorch.R.string.txt_loading))
                progress!!.setMessage(resources.getString(com.atg.demorch.R.string.txt_loading_message))
                progress!!.setCancelable(false) // disable dismiss by tapping outside of the dialog
            }
            progress!!.show()
        }
    }

    fun hideLoading() {
        runOnUiThread {
            if (progress != null)
                progress!!.dismiss()
        }
    }
}
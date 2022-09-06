package com.atg.demorch.utils

import android.R
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        observer()
    }

    abstract fun bindView()
    abstract fun observer()

    fun showAlert(title: String? = null, message: String) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(context)
                .setTitle(title ?: "Error")
                .setMessage(message)
                .setCancelable(true)
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
        requireActivity().runOnUiThread {
            if (progress == null) {
                progress = ProgressDialog(context)
                progress!!.setTitle("Loading")
                progress!!.setMessage("Wait while loading...")
                progress!!.setCancelable(true) // disable dismiss by tapping outside of the dialog
            }
            progress!!.show()
        }
    }

    fun hideLoading() {
        requireActivity().runOnUiThread {
            if (progress != null)
                progress!!.dismiss()
        }
    }
}
package com.example.github.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.example.github.databinding.ErrorBinding

class ErrorDialog(private val context: Context) {
    private val dialog: Dialog = Dialog(context)

    fun show() {
        val binding = ErrorBinding.inflate((context as Activity).layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing && !(context as Activity).isDestroyed) {
            dialog.dismiss()
        }
    }
}

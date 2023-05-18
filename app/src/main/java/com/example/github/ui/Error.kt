package com.example.github.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import com.example.github.R
import com.example.github.databinding.ErrorDialogBinding

class ErrorDialog(private val context: Context) {
    private val dialog: Dialog = Dialog(context)

    fun show(errorMessage: String) {
        val binding = ErrorDialogBinding.inflate((context as Activity).layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.setCancelable(false)
        binding.errorDescription.text = errorMessage
        dialog.show()

        binding.errorButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun dismiss() {
        if (dialog.isShowing && !(context as Activity).isDestroyed) {
            dialog.dismiss()
        }
    }
}

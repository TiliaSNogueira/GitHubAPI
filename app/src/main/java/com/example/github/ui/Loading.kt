package com.example.github.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.example.github.databinding.LoadingBinding

class Loading(private val context: Context) {
    private val dialog: Dialog = Dialog(context)

    init {
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    fun show() {
        val binding = LoadingBinding.inflate((context as Activity).layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setLayout(-1, -1)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing && !(context as Activity).isDestroyed) {
            dialog.dismiss()
        }
    }
}
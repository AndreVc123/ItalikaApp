package com.dev.bluecode.loaderapplication

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class LoaderView(context: Context,attrs: AttributeSet?) : Dialog(context) {

    lateinit var view: Dialog

    init {
        view = getProgressDialogLoading( context, R.layout.view_loader)
    }

    fun setText(text: String) {
        view.findViewById<TextView>(R.id.txtLoader).text = text
    }

    fun setTextColor(color: Int) {
        view.findViewById<TextView>(R.id.txtLoader).setTextColor(context.resources.getColorStateList(color))
    }

    fun setShow(isShowing: Boolean) {
        if(isShowing) view.show() else view.dismiss()
    }


    private fun getProgressDialogLoading(context: Context, view: Int): Dialog {
        val progressDialog = Dialog(context)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setContentView(view)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)
        return progressDialog
    }
}
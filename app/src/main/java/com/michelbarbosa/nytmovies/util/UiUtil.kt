package com.michelbarbosa.nytmovies.util

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.michelbarbosa.nytmovies.R
import com.squareup.picasso.Picasso
import michel566.androidmodules.lightdialog.DialogType
import michel566.androidmodules.lightdialog.LightDialog

object UiUtil {

    fun progressDialog(context: Context?, message: String?): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 16f
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(ll)
        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
        return dialog
    }

    fun showDialog(context: Context?, resourceMessage: Int, dialogType: DialogType?) {
        if (context != null) {
            if (Util.checkminimalAPI(Build.VERSION_CODES.LOLLIPOP)) {
                val dialog: LightDialog =
                    LightDialog(context, context.resources?.getString(resourceMessage), dialogType)
                dialog.show()
            } else {
                showAlertDialog(context, context.resources?.getString(resourceMessage), dialogType)
            }
        }
    }

    fun showDialog(context: Context?, message: String, dialogType: DialogType?) {
        if (context != null) {
            if (Util.checkminimalAPI(Build.VERSION_CODES.LOLLIPOP)) {
                val dialog: LightDialog = LightDialog(context, message, dialogType)
                dialog.show()
            } else {
                showAlertDialog(context, message, dialogType)
            }
        }
    }

    private fun showAlertDialog(context: Context, msg: String?, dialogType: DialogType?) {
        val dialogBox = AlertDialog.Builder(context).create()
        val icon = context.resources.getDrawable(R.drawable.ic_error)
        dialogBox.setMessage(msg)
        when (dialogType) {
            DialogType.INFO -> {
                dialogBox.setTitle("Informação")
                icon.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorInfoDialog),
                    PorterDuff.Mode.SRC_IN
                )
            }
            DialogType.ALERT -> {
                dialogBox.setTitle("Atenção!")
                icon.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorAlertDialog),
                    PorterDuff.Mode.SRC_IN
                )
            }
            DialogType.ERROR -> {
                dialogBox.setTitle("Erro")
                icon.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorErrorDialog),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
        dialogBox.setIcon(icon)
        dialogBox.setCanceledOnTouchOutside(true)
        dialogBox.show()
    }

    fun loadPicture(imageView: ImageView?, uri: String?){
        if (!uri.isNullOrEmpty()){
            Picasso.get()
                .load(uri)
                .resize(210,140)
                .into(imageView)
        } else{
            Picasso.get()
                .load(R.drawable.image_not_available)
                .resize(210,140)
                .into(imageView)
        }
    }

    fun setTintFavoriteIcon(res: Resources?, imageView: ImageView?, isActive: Boolean){
        if (isActive) {
            imageView!!.setImageDrawable(res?.getDrawable(R.drawable.ic_star))
        } else {
            imageView!!.setImageDrawable(res?.getDrawable(R.drawable.ic_star_border))
        }
    }

}
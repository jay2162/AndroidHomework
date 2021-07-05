package com.app.androidhomework.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.BuildConfig
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.app.androidhomework.R
import com.app.androidhomework.databinding.CustomDialogBinding
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.snackbar.Snackbar


/**
 *  Retrieve Actual device width
 */
@Suppress("DEPRECATION")
fun getScreenWidth(context: Context): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}


/**
 *  Retrieve Actual device height
 */
@Suppress("DEPRECATION")
fun getScreenHeight(context: Context): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun Activity.logd(message: String) {
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        val view = snackbar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        val tv = view.findViewById<TextView>(R.id.snackbar_text)
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.tab_label))
        tv.setTextColor(ContextCompat.getColor(context!!, R.color.white))
    }.show()
}

fun View.snackbarFull(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        val view = snackbar.view
        val tv = view.findViewById<TextView>(R.id.snackbar_text)
        tv.isSingleLine = false
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.tab_label))
        tv.setTextColor(ContextCompat.getColor(context!!, R.color.white))
    }.show()
}

fun customProgressDrawable(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun Context.showDialogMessages(
    message: String = "",
    title: String = "",
    positiveText: String = getString(android.R.string.ok),
    negativeText: String = getString(android.R.string.cancel),
    listener: View.OnClickListener? = null,
    showBothButton: Boolean = false,
    setCanceledOnTouchOutside: Boolean = true,
    setCancelable: Boolean = true,
    isShowRedColor: Boolean = true,
    isShowOnLeftRedColor: Boolean = false
) {
    val alertDialog = AlertDialog.Builder(this, R.style.TransparentProgressDialog).create()
    val view = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null, false)
    val binding = DataBindingUtil.bind<CustomDialogBinding>(view)
    alertDialog.setView(binding!!.root)
    alertDialog.setCancelable(setCancelable)
    alertDialog.setCanceledOnTouchOutside(setCanceledOnTouchOutside)
    if (isShowRedColor) {
        binding.txtPositive.setTextColor(ContextCompat.getColor(this, R.color.black))
    } else if (isShowOnLeftRedColor) {
        binding.txtPositive.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.txtNegative.setTextColor(ContextCompat.getColor(this, R.color.black))
    } else {
        binding.txtPositive.setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    binding.txtPositive.text = positiveText
    binding.txtNegative.text = negativeText
    binding.txtTitle.text = title
    binding.txtMessage.text = message


    if (TextUtils.isEmpty(title)) {
        binding.txtTitle.visibility = View.GONE
    } else {
        binding.txtTitle.visibility = View.VISIBLE
    }
    if (TextUtils.isEmpty(message)) {
        binding.txtMessage.visibility = View.GONE
    } else {
        binding.txtMessage.visibility = View.VISIBLE
    }
    if (showBothButton) {
        binding.divider.visibility = View.VISIBLE
        binding.txtNegative.visibility = View.VISIBLE
    } else {
        binding.divider.visibility = View.GONE
        binding.txtNegative.visibility = View.GONE
    }

    binding.txtPositive.setOnClickListener { v ->
        listener?.onClick(v)
        alertDialog.dismiss()
    }
    binding.txtNegative.setOnClickListener { v ->
        listener?.onClick(v)
        alertDialog.dismiss()
    }
    alertDialog.show()
}


fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

fun Fragment.setNavigationResult(result: String, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}



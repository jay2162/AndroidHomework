package com.app.androidhomework.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import com.app.androidhomework.R

object DialogHelper {
    /**
     * @param context    activity context
     * @param title      dialog title, if null or blank then it will not show
     * @param message    dialog message
     * @param buttonText label of the button of dialog
     * @return object of alert dialog
     */
    fun showDialog(
        context: Context?,
        title: String?,
        message: String?,
        buttonText: String?
    ): AlertDialog? {
        try {
            val builder =
                AlertDialog.Builder(context, R.style.LightDialogTheme)
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title)
            }
            builder.setMessage(message)
            builder.setCancelable(true)
            builder.setPositiveButton(buttonText, null)
            return builder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * @param context    activity context
     * @param title      dialog title, if null or blank then it will not show
     * @param message    dialog message
     * @param buttonText label of the button of dialog
     * @param listener   button click listener
     * @return object of alert dialog
     */
    fun showDialog(
        context: Context?,
        title: String?,
        message: String?,
        buttonText: String?,
        listener: DialogInterface.OnClickListener
    ): AlertDialog? {
        try {
            val builder =
                AlertDialog.Builder(context, R.style.LightDialogTheme)
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title)
            }
            builder.setMessage(message)
            builder.setCancelable(true)
            builder.setPositiveButton(buttonText, listener)
            return builder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * @param context    activity context
     * @param title      dialog title, if null or blank then it will not show
     * @param message    dialog message
     * @param cancelable dialog is cancellable or not
     * @param buttonText label of the button of dialog
     * @param listener   button click listener
     * @return object of alert dialog
     */
    fun showDialog(
        context: Context?,
        title: String?,
        message: String?,
        cancelable: Boolean,
        buttonText: String?,
        listener: DialogInterface.OnClickListener?
    ): AlertDialog? {
        try {
            val builder =
                AlertDialog.Builder(context, R.style.LightDialogTheme)
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title)
            }
            builder.setMessage(message)
            builder.setCancelable(cancelable)
            builder.setPositiveButton(buttonText, listener)
            return builder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * @param context                activity context
     * @param title                  dialog title, if null or blank then it will not show
     * @param message                dialog message
     * @param positiveButtonText     label of the positive button of dialog
     * @param negativeButtonText     label of the negative button of dialog
     * @param positiveButtonListener positive button click listener
     * @param negativeButtonListener negative button click listener
     * @return object of alert dialog
     */
    fun showDialog(
        context: Context?,
        title: String?,
        message: String?,
        positiveButtonText: String?,
        negativeButtonText: String?,
        positiveButtonListener: DialogInterface.OnClickListener?,
        negativeButtonListener: DialogInterface.OnClickListener?
    ): AlertDialog? {
        try {
            val builder =
                AlertDialog.Builder(context, R.style.LightDialogTheme)
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title)
            }
            builder.setMessage(message)
            builder.setCancelable(true)
            builder.setPositiveButton(positiveButtonText, positiveButtonListener)
            builder.setNegativeButton(negativeButtonText, negativeButtonListener)
            return builder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * @param context                activity context
     * @param title                  dialog title, if null or blank then it will not show
     * @param message                dialog message
     * @param cancelable             dialog is cancellable or not
     * @param positiveButtonText     label of the positive button of dialog
     * @param negativeButtonText     label of the negative button of dialog
     * @param positiveButtonListener positive button click listener
     * @param negativeButtonListener negative button click listener
     * @return object of alert dialog
     */
    fun showDialog(
        context: Context?,
        title: String?,
        message: String?,
        cancelable: Boolean,
        positiveButtonText: String?,
        negativeButtonText: String?,
        positiveButtonListener: DialogInterface.OnClickListener?,
        negativeButtonListener: DialogInterface.OnClickListener?
    ): AlertDialog? {
        try {
            val builder =
                AlertDialog.Builder(context, R.style.LightDialogTheme)
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title)
            }
            builder.setMessage(message)
            builder.setCancelable(cancelable)
            builder.setPositiveButton(positiveButtonText, positiveButtonListener)
            builder.setNegativeButton(negativeButtonText, negativeButtonListener)
            return builder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
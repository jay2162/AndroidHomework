package com.app.androidhomework.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.app.androidhomework.R
import com.app.androidhomework.listener.OnDialogButtonClickListener

/**
 * Dialog helper class
 */
object DialogHelperCustom {

    /**
     * Display alert dialog
     *
     * @param context    activity context
     * @param title      dialog title, if null or blank then it will not show
     * @param message    dialog message
     * @param isCancelable    dialog is cancellable or not
     * @param positiveButtonText label of the positive button of dialog
     * @param negativeButtonText label of the negative button of dialog
     * @param onDialogPositiveButtonClickListener   positive button click listener
     * @param onDialogNegativeButtonClickListener   negative button click listener
     * @return object of alert dialog
     */
    fun showDialog(
        context: Context,
        icon: Int?,
        title: String,
        message: String,
        isCancelable: Boolean,
        positiveButtonText: String,
        negativeButtonText: String,
        onDialogPositiveButtonClickListener: OnDialogButtonClickListener?,
        onDialogNegativeButtonClickListener: OnDialogButtonClickListener?
    ): Dialog? {

        try {

            val dialog = Dialog(context, R.style.AlertDialogTheme)
            dialog.setContentView(R.layout.custom_alert_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(isCancelable)

            /**
             * set dialog box's icon drawable (Optional)
             */
            val ivIcon: ImageView = dialog.findViewById(R.id.ivIcon)
            if (icon == null) {
                ivIcon.visibility = View.GONE
            } else {
                ivIcon.visibility = View.VISIBLE
                ivIcon.setBackgroundResource(icon)
            }

            /**
             * set dialog box's title (Optional)
             */
            val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)
            if (title.isNotEmpty()) {
                tvTitle.text = title
                tvTitle.visibility = View.VISIBLE
            } else {
                tvTitle.visibility = View.GONE
            }

            /**
             *  set dialog box message
             */
            val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
            if (message.isNotEmpty()) {
                tvMessage.text = message
                tvMessage.visibility = View.VISIBLE
            } else {
                tvMessage.visibility = View.GONE
            }

            /**
             * set dialog box's positive button text
             */
            val tvPositive: TextView = dialog.findViewById(R.id.tvPositive)
            if (positiveButtonText.isNotEmpty()) {
                tvPositive.text = positiveButtonText
                tvPositive.visibility = View.VISIBLE
                /**
                 * Check onDialogPositiveButtonClickListener!=null
                 */
                onDialogPositiveButtonClickListener?.let {
                    /**
                     * Handle dialog box's positive button click event
                     */
                    tvPositive.setOnClickListener { view ->
                        it.onDialogButtonClicked(view)
                        dialog.dismiss()
                    }
                }

            } else {
                tvPositive.visibility = View.GONE
            }

            /**
             * set dialog box's negative button text
             */
            val tvNegative: TextView = dialog.findViewById(R.id.tvNegative)
            if (negativeButtonText.isNotEmpty()) {
                tvNegative.text = negativeButtonText
                tvNegative.visibility = View.VISIBLE

                /**
                 * Check onDialogNegativeButtonClickListener!=null
                 */
                onDialogNegativeButtonClickListener?.let {
                    /**
                     * Handle dialog box's negative button click event
                     */
                    tvNegative.setOnClickListener { view ->
                        it.onDialogButtonClicked(view)
                        dialog.dismiss()
                    }
                }
            } else {
                tvNegative.visibility = View.GONE
            }

            dialog.show()
            return dialog

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * Simplify alert dialog with title, message param to show only generic or error message
     *
     * @param context       activity context
     * @param title         dialog title
     * @param message       dialog message
     * @return object of alert dialog
     */
    fun showDialog(
        context: Context,
        title: String,
        message: String
    ): Dialog? {

        return showDialog(
            context,
            null,
            title,
            message,
            true,
            context.getString(R.string.ok),
            "",
            null,
            null
        )
    }
}
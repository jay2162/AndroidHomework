package com.app.androidhomework.base


import android.accounts.NetworkErrorException
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.app.androidhomework.R
import com.app.androidhomework.network.SessionManager
import com.app.androidhomework.utils.CommonUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.app.androidhomework.utils.DialogHelperCustom
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLException
import javax.net.ssl.SSLPeerUnverifiedException

/**
 * Base class for Fragment with generalise methods
 */
abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    private var alertDialog: Dialog? = null
    lateinit var binding: B
    lateinit var navController: NavController
    lateinit var navSubController: NavController
    var actionBar: ActionBar? = null
    private var progressDialog: Dialog? = null

    @get:LayoutRes
    abstract val layoutId: Int

    @Inject
    lateinit var session: SessionManager

    /**
     * display progress dialog
     */


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false)
        binding = DataBindingUtil.bind<B>(view)!!
        binding.lifecycleOwner = viewLifecycleOwner
        session = SessionManager(requireActivity())
        binding.executePendingBindings()
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)

        session.isNotify = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressDialog = CommonUtils.showLoadingDialog(requireActivity());
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT < 26) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }


    fun isValidEmail(email: String): Boolean {
        val ragEx: Regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return email.matches(ragEx)
    }

    fun showProgress() {
        hideProgress()
        if (progressDialog != null && !progressDialog!!.isShowing) {
            progressDialog!!.show()
        }
    }

    fun hideProgress() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }


    /**
     * display api error message in alert dialog
     *
     * @param response       API response as json string
     */
    protected fun displayAPIMessage(response: String) {
        try {

            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog?.dismiss()
            }
            alertDialog =
                DialogHelperCustom.showDialog(
                    requireContext(),
                    "",
                    getString(R.string.generic_server_error)
                )
        } catch (e: Exception) {

            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog?.dismiss()
            }
            alertDialog = DialogHelperCustom.showDialog(requireContext(), "", response)
        }
    }

    /**
     * display message in alert dialog
     *
     * @param message       Message
     */
    protected fun displayMessage(message: String) {

        if (!TextUtils.isEmpty(message)) {

            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog?.dismiss()
            }
            alertDialog = DialogHelperCustom.showDialog(requireContext(), "", message)
        }
    }

    /**
     * display message in alert dialog
     *
     * @param title         Title
     * @param message       Message
     */
    protected fun displayMessage(title: String, message: String) {

        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog?.dismiss()
        }
        alertDialog = DialogHelperCustom.showDialog(requireContext(), title, message)
    }

    /**
     * display error in alert dialog
     *
     * @param message Message
     */
    protected fun displayError(message: String) {
        var message = message

        if (!TextUtils.isEmpty(message)) {

            if (message == getString(R.string.no_internet)
                || message.contains(SocketTimeoutException::class.java.name)
                || message.contains(UnknownHostException::class.java.name)
                || message.contains(NetworkErrorException::class.java.name)
                || message.contains(SSLException::class.java.name)
                || message.contains(SSLPeerUnverifiedException::class.java.name)
                || message.contains(JSONException::class.java.name)
                || message.contains(ConnectException::class.java.name)
                || message.contains(SocketException::class.java.name)
                || Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && message.contains(
                    org.apache.http.conn.ConnectTimeoutException::class.java.name
                )
            ) {
                message = getString(R.string.no_internet)
            } else {
                message = getString(R.string.generic_server_error)
            }

            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }

            alertDialog =
                DialogHelperCustom.showDialog(requireContext(), "", message)
        }
    }

    fun setCenterPosition() {
        if (tvTitle != null) {
            tvTitle!!.gravity = Gravity.CENTER
        } else {
            tvTitle!!.gravity = Gravity.START
        }
    }

    fun setSecondTitle(secondTitle: String) {
        if (tvTitleDec != null) {
            tvTitleDec.setText(secondTitle)
            tvTitleDec.visibility = View.VISIBLE
        } else {
            tvTitleDec.visibility = View.INVISIBLE
        }
    }


    fun setHomeEnable(enable: Boolean) {
        if (actionBar != null) {
            actionBar!!.setHomeButtonEnabled(enable)
            actionBar!!.setDisplayShowTitleEnabled(false)
            actionBar!!.setDisplayShowCustomEnabled(enable)
            actionBar!!.setDisplayHomeAsUpEnabled(enable)
            actionBar!!.setHomeButtonEnabled(enable)
            actionBar!!.setDisplayShowHomeEnabled(enable)
        }
    }

    fun setHomeEnable(buttonId: Int) {
        if (actionBar != null) {
            actionBar!!.setHomeButtonEnabled(true)
            actionBar!!.setDisplayShowTitleEnabled(false)
            actionBar!!.setHomeAsUpIndicator(buttonId)
            actionBar!!.setDisplayShowCustomEnabled(true)
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

}
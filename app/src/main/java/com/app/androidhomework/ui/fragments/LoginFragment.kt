package com.app.androidhomework.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.app.androidhomework.R
import com.app.androidhomework.base.BaseFragment
import com.app.androidhomework.databinding.FragmentLoginBinding
import com.app.androidhomework.utils.showDialogMessages
import com.app.androidhomework.utils.snackbar
import com.app.androidhomework.viewmodel.LoginViewModel
import com.app.androidhomework.utils.Constants

class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener {

    private val TAG = LoginFragment::class.java.simpleName

    var accessToken: String? = null
    lateinit var viewModel: LoginViewModel
    override val layoutId: Int = R.layout.fragment_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    requireActivity().showDialogMessages(
                        "Are you sure you want to exit?",
                        "Exit App",
                        "Yes", "No",
                        listener = View.OnClickListener {
                            when (it.id) {
                                R.id.txt_positive -> {
                                    session.setStringDataByKey("noti", "")
                                    requireActivity().finish()
                                }
                            }
                        },
                        showBothButton = true,
                        setCanceledOnTouchOutside = false,
                        setCancelable = false,
                        isShowRedColor = true
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)
            .get(LoginViewModel::class.java)

        binding.customToolbar.tvTitle.setText(R.string.let_get_something)
        binding.customToolbar.tvTitleDec.visibility = View.VISIBLE
        binding.customToolbar.tvTitleDec.setText(R.string.good_see_you_back)

        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

        val mSpanString = SpannableString(getString(R.string.dont_have_account))
        mSpanString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.forgotColor
                )
            ), 23, mSpanString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        mSpanString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navController.navigate(R.id.action_loginFragment_to_signupFragment)
                }
            },
            23,
            mSpanString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mSpanString.setSpan(
            object : UnderlineSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            },
            23,
            mSpanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvSignup.setText(mSpanString)
        binding.tvSignup.setMovementMethod(LinkMovementMethod.getInstance())
        binding.tvSignup.setHighlightColor(Color.TRANSPARENT)

        observeViewModel()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvForgotPassword -> {
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            }
            R.id.btnLogin -> {
                if (validation()) {
                    viewModel.loginAPI(
                        binding!!.edtEmail.text.toString().trim(),
                        binding!!.edtPassword.text.toString().trim(),
                        session.getDeviceToken()!!,
                        "android"
                    )
                }
            }
        }
    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding!!.edtEmail.text.toString().trim())) {
            binding!!.root.snackbar("Enter your email")
            binding!!.edtEmail.requestFocus()
            return false
        } else if (!isValidEmail(binding!!.edtEmail.text.toString().trim())) {
            binding!!.root.snackbar("Enter your valid email")
            binding!!.edtEmail.requestFocus()
            return false
        } else if (TextUtils.isEmpty(binding!!.edtPassword.text.toString().trim())) {
            binding!!.root.snackbar("Enter your password.")
            binding!!.edtPassword.requestFocus()
            return false
        }
        return true
    }

    fun observeViewModel() {
        viewModel.loginData.observe(requireActivity(), androidx.lifecycle.Observer {
            it.let {
                Log.e(TAG, it.toString())
                session.saveAuthorizedUser(it)
                session.setBooleanDataByKey(Constants.IS_LOGING, true)
                navController.navigate(R.id.action_loginFragment_to_landingFragment)
            }
        })
        viewModel.loginError.observe(requireActivity(), androidx.lifecycle.Observer {
            it.let {
                if (it) {
                    binding!!.root.snackbar(viewModel.apiError.value!!)
                } else {

                }

            }
        })

        viewModel.loadingProgressBar.observe(requireActivity(), androidx.lifecycle.Observer {
            it.let {
                if (it) {
                    showProgress()
                } else {
                    hideProgress()
                }

            }
        })

    }
}
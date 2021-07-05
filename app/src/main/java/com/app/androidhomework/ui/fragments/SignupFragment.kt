package com.app.androidhomework.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.app.androidhomework.R
import com.app.androidhomework.base.BaseFragment
import com.app.androidhomework.databinding.FragmentSignupBinding
import com.app.androidhomework.utils.snackbar
import com.app.androidhomework.viewmodel.SignUpViewModel

import com.app.androidhomework.utils.Constants

import java.util.regex.Matcher
import java.util.regex.Pattern


class SignupFragment : BaseFragment<FragmentSignupBinding>(), View.OnClickListener {

    override val layoutId: Int = R.layout.fragment_signup
    private val TAG = LoginFragment::class.java.simpleName

    lateinit var viewModel: SignUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customToolbar.tvTitle.setText(R.string.getting_started)
        binding.customToolbar.tvTitleDec.visibility = View.VISIBLE
        binding.customToolbar.tvTitleDec.setText(R.string.create_account_continue)
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        initView()
    }

    private fun initView() {
        binding.etPasswordLayout.setEndIconOnClickListener {
            if ( binding.edtConPassword.transformationMethod == null) {
                binding.edtConPassword.transformationMethod = PasswordTransformationMethod()
            } else {
                binding.edtConPassword.transformationMethod = null
            }
            binding.edtConPassword.setSelection(binding.edtConPassword.text.length)

            if ( binding.edtPassword.transformationMethod == null) {
                binding.edtPassword.transformationMethod = PasswordTransformationMethod()
            } else {
                binding.edtPassword.transformationMethod = null
            }
            binding.edtPassword.setSelection(binding.edtPassword.text.length)
        }
        val mSpanString = SpannableString(getString(R.string.already_have_account))
        //val mTNCSpanString = SpannableString(getString(R.string.already_have_account))

        mSpanString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.forgotColor)),
            25,
            mSpanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mSpanString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navController.navigateUp()
                }
            },
            25,
            mSpanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mSpanString.setSpan(
            object : UnderlineSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            },
            25,
            mSpanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.tvLogin.setText(mSpanString)
        binding.tvLogin.setMovementMethod(LinkMovementMethod.getInstance())
        binding.tvLogin.setHighlightColor(Color.TRANSPARENT)

        mSpanString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.forgotColor
                )
            ),
            25,
            mSpanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mSpanString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navController.navigateUp()
                }
            },
            25,
            mSpanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mSpanString.setSpan(
            object : UnderlineSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            },
            25,
            mSpanString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.tvLogin.setText(mSpanString)
        binding.tvLogin.setMovementMethod(LinkMovementMethod.getInstance())
        binding.tvLogin.setHighlightColor(Color.TRANSPARENT)

        observeViewModel()

        binding.btnSignup.setOnClickListener(this)
    }

    fun observeViewModel() {
        viewModel.signupData.observe(requireActivity(), androidx.lifecycle.Observer {
            it.let {
                Log.e(TAG, it.toString())
                session.saveAuthorizedUser(it)
                session.setBooleanDataByKey(Constants.IS_LOGING, true)
                navController.navigate(R.id.action_signupFragment_to_nav_landing_fragment)
            }
        })
        viewModel.signupError.observe(requireActivity(), androidx.lifecycle.Observer {
            it.let {
                if (it) {
                    binding!!.root.snackbar(viewModel.apiError.value!!)
//                    binding!!.error.visibility = View.VISIBLE
                } else {
//                    binding!!.error.visibility = View.GONE
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSignup -> {
                removePhoneKeypad()
                if (validation()) {
                    viewModel.signupAPI(
                        binding!!.edtName.text.toString().trim(), binding!!.edtEmail.text.toString().trim(),
                        binding!!.edtPassword.text.toString().trim()
                    )
                }
            }
        }
    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding!!.edtName.text.toString().trim())) {
            binding!!.root.snackbar("Please enter name")
            binding!!.edtName.requestFocus()
            return false
        } else if (TextUtils.isEmpty(binding!!.edtEmail.text.toString().trim())) {
            binding!!.root.snackbar("Please enter email")
            binding!!.edtEmail.requestFocus()
            return false
        } else if (!isValidEmail(binding!!.edtEmail.text.toString().trim())) {
            binding!!.root.snackbar("Please enter valid email")
            binding!!.edtEmail.requestFocus()
            return false
        } else if (TextUtils.isEmpty(binding!!.edtPassword.text.toString().trim())) {
            binding!!.root.snackbar("Please enter password")
            binding!!.edtPassword.requestFocus()
            return false
        } else if (binding!!.edtPassword.text.toString().trim().length < 8) {
            binding!!.root.snackbar("Password must be 8 characters long with at least one uppercase letter, one special character and one number")
            binding!!.edtPassword.requestFocus()
            return false
        } else if (!isPartem(binding!!.edtPassword.text.toString().trim())) {
            binding!!.root.snackbar("Password must be 8 characters long with at least one uppercase letter, one special character and one number")
            binding!!.edtPassword.requestFocus()
            return false
        } else if (TextUtils.isEmpty(binding!!.edtConPassword.text.toString().trim())) {
            binding!!.root.snackbar("Please enter confirm  password")
            binding!!.edtConPassword.requestFocus()
            return false
        } else if (binding!!.edtPassword.text.toString().trim() != binding!!.edtConPassword.text.toString().trim()) {
            binding!!.root.snackbar("Both password doesn't match")
            binding!!.edtConPassword.requestFocus()
            return false
        }
        return true
    }

    fun isPartem(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher

        val PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()
    }

    fun removePhoneKeypad() {
        val inputManager = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val binder = view?.windowToken
        inputManager.hideSoftInputFromWindow(
            binder,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
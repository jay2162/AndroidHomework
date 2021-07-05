package com.app.androidhomework.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.app.androidhomework.R
import com.app.androidhomework.base.BaseFragment
import com.app.androidhomework.databinding.FragmentForgotPasswordBinding
import com.app.androidhomework.utils.snackbar
import com.app.androidhomework.viewmodel.ForgotPasswordViewModel

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(), View.OnClickListener {
    override val layoutId: Int = R.layout.fragment_forgot_password
    lateinit var viewModel: ForgotPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)
        observeViewModel()
        binding.customToolbar.tvTitle.setText(R.string.forgot_password)
        binding.customToolbar.tvTitleDec.visibility = View.VISIBLE
        binding.customToolbar.tvTitleDec.setText(R.string.enter_reset_email)
        initView()
    }

    private fun initView() {
        binding.btnResetPassword.setOnClickListener(this)
        binding.tvBackToLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvBackToLogin -> {
                navController.navigateUp()
            }
            R.id.btnResetPassword -> {
                if (validation()) {
                    viewModel.forgotAPI(binding!!.edtEmail.text.toString().trim())
                }

            }
        }
    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding!!.edtEmail.text.toString().trim())) {
            binding!!.root.snackbar("Enter your email.")
            binding!!.edtEmail.requestFocus()
            return false
        } else if (!isValidEmail(binding!!.edtEmail.text.toString().trim())) {
            binding!!.root.snackbar("Enter your valid email")
            binding!!.edtEmail.requestFocus()
            return false
        }
        return true
    }

    fun observeViewModel() {
        viewModel.loginData.observe(requireActivity(), androidx.lifecycle.Observer {
            it.let {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            }
        })
        viewModel.loginError.observe(requireActivity(), androidx.lifecycle.Observer {
            it.let {
                if (it) {
                    Toast.makeText(requireActivity(), viewModel.apiError.value, Toast.LENGTH_SHORT)
                        .show()
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
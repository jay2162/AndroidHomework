package com.app.androidhomework.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.app.androidhomework.R
import com.app.androidhomework.base.BaseFragment
import com.app.androidhomework.databinding.FragmentProfileBinding
import com.app.androidhomework.utils.showDialogMessages

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val layoutId: Int = R.layout.fragment_profile

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
                        listener = {
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
        initView()
    }

    override fun onResume() {
        super.onResume()
        binding.tvFullName.text = session.getAuthorizedUser()!!.person.display_name
        binding.tvEmail.text = session.getAuthorizedUser()!!.person.email
    }

    private fun initView() {
        binding.customToolbar.tvTitle.text = getString(R.string.my_profile)
        binding.customToolbar.tvTitle.gravity = Gravity.CENTER

        binding.tvLogout.setOnClickListener {
            requireActivity().showDialogMessages(
                "Are you sure you want to logout?",
                "Logout",
                "Yes", "No",
                listener = View.OnClickListener {
                    when (it.id) {
                        R.id.txt_positive -> {
                            session.setLogout()
                            val mainNavView =
                                requireActivity().findViewById<View>(R.id.navHostFragment)
                            Navigation.findNavController(mainNavView).navigate(R.id.loginFragment)
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

}
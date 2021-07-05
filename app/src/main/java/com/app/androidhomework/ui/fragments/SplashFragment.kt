package com.app.androidhomework.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.app.androidhomework.R
import com.app.androidhomework.base.BaseFragment
import com.app.androidhomework.databinding.FragmentSplashBinding
import com.app.androidhomework.utils.Constants


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val layoutId: Int = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            if (session.getBooleanDataByKey(Constants.IS_LOGING)) {
                navController.navigate(R.id.action_splashFragment_to_nav_landing_fragment)
            } else {
                navController.navigate(R.id.action_splashFragment_to_loginFragment2)
            }
        }, 3000)
    }

}
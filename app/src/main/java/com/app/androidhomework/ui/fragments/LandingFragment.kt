package com.app.androidhomework.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.app.androidhomework.R
import com.app.androidhomework.base.BaseFragment
import com.app.androidhomework.databinding.FragmentLandingBinding

class LandingFragment : BaseFragment<FragmentLandingBinding>() {

    override val layoutId: Int = R.layout.fragment_landing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_landing_fragment)



        binding.bottomNavigationView.setupWithNavController(navController)

    }
}
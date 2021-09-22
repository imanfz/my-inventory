package com.imanfz.myinventory.presentation

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.imanfz.myinventory.R
import com.imanfz.myinventory.databinding.ActivityMainBinding
import com.imanfz.myinventory.presentation.base.BaseActivity

class MainActivity :
    BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val navController = findNavController(R.id.navigation_host_fragment)
        binding.navigationView.setupWithNavController(navController)
    }
}
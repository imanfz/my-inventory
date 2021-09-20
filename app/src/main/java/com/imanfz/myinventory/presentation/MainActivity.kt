package com.imanfz.myinventory.presentation

import android.os.Bundle
import com.imanfz.myinventory.databinding.ActivityMainBinding
import com.imanfz.myinventory.presentation.base.BaseActivity

class MainActivity :
    BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
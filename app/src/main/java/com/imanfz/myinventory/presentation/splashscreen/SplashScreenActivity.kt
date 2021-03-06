package com.imanfz.myinventory.presentation.splashscreen

import android.os.Bundle
import android.os.Handler
import com.imanfz.myinventory.databinding.ActivitySplashScreenBinding
import com.imanfz.myinventory.presentation.MainActivity
import com.imanfz.myinventory.presentation.base.BaseActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding>(
        ActivitySplashScreenBinding::inflate
    ) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        Handler(mainLooper).postDelayed({
            startActivity(intentFor<MainActivity>().clearTask().newTask())
            finish()
        }, 3000)
    }
}
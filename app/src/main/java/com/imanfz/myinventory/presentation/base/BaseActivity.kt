package com.imanfz.myinventory.presentation.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import com.imanfz.myinventory.R
import com.imanfz.myinventory.utils.hide
import com.imanfz.myinventory.utils.show
import org.jetbrains.anko.find

abstract class BaseActivity<B: ViewBinding>(
    val bindingFactory: (LayoutInflater
    ) -> B) : AppCompatActivity() {

    val binding : B by lazy {
        bindingFactory(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        hideSystemUI()

        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryDark, theme)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setupToolbar(title: String, isBackEnabled: Boolean = false, backgroundBlue: Boolean? = false) {
        val tvTitle = binding.root.find<AppCompatTextView>(R.id.tv_title)
        val btnBack = binding.root.find<AppCompatImageView>(R.id.btn_back)
        val clToolbar = binding.root.find<ConstraintLayout>(R.id.cl_toolbar)
        tvTitle.text = title
        if (isBackEnabled) {
            btnBack.show()
            btnBack.setOnClickListener { onBackPressed() }
        } else {
            btnBack.hide()
        }

        if (backgroundBlue == true){
            clToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.white))
            btnBack.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_left_white))
        }
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Tell the window that we want to handle/fit any system windows
            WindowCompat.setDecorFitsSystemWindows(window, false)

            val controller = binding.root.windowInsetsController

            // Hide the keyboard (IME)
            controller?.hide(WindowInsets.Type.ime())

            // Sticky Immersive is now ...
            controller?.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            // When we want to hide the system bars
            controller?.hide(WindowInsets.Type.systemBars())
        } else {
            //noinspection
            @Suppress("DEPRECATION")
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }
}
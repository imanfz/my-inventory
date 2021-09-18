package com.imanfz.myinventory.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.imanfz.myinventory.R
import com.imanfz.myinventory.utils.hide

abstract class BaseFragment<B : ViewBinding>(
    private val bindingFactory : (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment() {

    private var _binding : B? = null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingFactory.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupToolbar(title: String, backgroundBlue: Boolean? = false) {

        val tvTitle = binding?.root?.findViewById<AppCompatTextView>(R.id.tv_title)
        val btnBack = binding?.root?.findViewById<AppCompatImageView>(R.id.btn_back)
        val clToolbar = binding?.root?.findViewById<ConstraintLayout>(R.id.cl_toolbar)

        if (tvTitle != null) {
            tvTitle.text = title
        }

        btnBack?.hide()

        if (backgroundBlue!!){
            clToolbar?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
            tvTitle?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            btnBack?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_left_white))
        }
    }
}
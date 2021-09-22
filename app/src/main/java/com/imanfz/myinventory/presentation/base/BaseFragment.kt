package com.imanfz.myinventory.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.imanfz.myinventory.R
import com.imanfz.myinventory.utils.hide
import com.imanfz.myinventory.utils.show
import org.jetbrains.anko.find

abstract class BaseFragment<B : ViewBinding>(
    private val bindingFactory : (
        LayoutInflater,
        ViewGroup?,
        Boolean
    ) -> B) : Fragment() {

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

    fun setupToolbar(title: String, isEditEnabled: Boolean = false, isDeleteEnabled: Boolean = false) {

        val tvTitle = binding?.root?.find<AppCompatTextView>(R.id.tv_title)
        val btnBack = binding?.root?.find<AppCompatImageView>(R.id.btn_back)
        val btnDelete = binding?.root?.find<AppCompatImageView>(R.id.btn_delete)
        val btnEdit = binding?.root?.find<AppCompatImageView>(R.id.btn_edit)

        if (tvTitle != null) {
            tvTitle.text = title
        }

        btnBack?.hide()

        if (isEditEnabled) btnEdit?.show() else  btnEdit?.hide()
        if (isDeleteEnabled) btnDelete?.show() else btnDelete?.hide()
    }
}
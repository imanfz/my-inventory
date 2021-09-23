package com.imanfz.myinventory.utils

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.imanfz.myinventory.databinding.DialogImageViewerBinding

class ImageViewerDialog : DialogFragment() {

    private var _binding : DialogImageViewerBinding? = null
    val binding get() = _binding

    companion object {
        @JvmStatic
        fun newInstance(image: String?, byteArray: ByteArray?, code: Int) = ImageViewerDialog().apply {
            arguments = Bundle().apply {
                putString(EXTRA_IMAGE, image)
                putByteArray(EXTRA_BYTE, byteArray)
                putInt(CODE, code)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogImageViewerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.codeImg?.apply {
            when(arguments?.getInt(CODE)) {
                0 -> loadImageFromUri(Uri.parse(arguments?.getString(EXTRA_IMAGE)))
                else -> arguments?.getByteArray(EXTRA_BYTE)?.let { loadImageFromByteArray(it) }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.setLayout(width, height)
        }
    }
}
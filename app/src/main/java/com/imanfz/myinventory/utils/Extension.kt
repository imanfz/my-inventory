package com.imanfz.myinventory.utils

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.imanfz.myinventory.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ImageView.loadImageFromByteArray(byte: ByteArray) {
    Glide.with(this.context)
        .load(byte)
        .apply (
            RequestOptions
                .circleCropTransform()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
        )
        .into(this)
}

fun ImageView.loadImageFromUri(uri: Uri) {
    Glide.with(this.context)
        .load(uri)
        .apply (
            RequestOptions
                .circleCropTransform()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
        )
        .into(this)
}

fun Activity.hideSoftKeyboard(){
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
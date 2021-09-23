package com.imanfz.myinventory.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.imanfz.myinventory.R
import com.imanfz.myinventory.viewmodel.AppViewModel
import com.imanfz.myinventory.viewmodel.ViewModelFactory
import java.io.ByteArrayOutputStream


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ImageView.loadCircleImageFromByteArray(byte: ByteArray) {
    if (byte.isEmpty()) return

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
                .placeholderOf(R.drawable.ic_image)
                .error(R.drawable.ic_image)
        )
        .into(this)
}

fun ImageView.loadImageFromByteArray(byte: ByteArray) {
    if (byte.isEmpty()) return

    Glide.with(this.context)
        .load(byte)
        .apply (
            RequestOptions
                .placeholderOf(R.drawable.ic_image)
                .error(R.drawable.ic_image)
        )
        .into(this)
}

fun ImageView.loadCircleImageFromUri(uri: Uri) {
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

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.obtainViewModel(): AppViewModel {
    val factory = ViewModelFactory.getInstance(this.application)
    return ViewModelProvider(this, factory).get(AppViewModel::class.java)
}

fun Context.convertToByteArray(id: Int): ByteArray {
    val drawable = ContextCompat.getDrawable(this, id)
    val bitmap = drawable!!.toBitmap()
    return bitmap.getBitmapBytes()
}

fun Bitmap.getBitmapBytes(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

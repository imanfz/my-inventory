package com.imanfz.myinventory.presentation.friend.detail

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.imanfz.myinventory.R
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity
import com.imanfz.myinventory.databinding.ActivityDetailFriendBinding
import com.imanfz.myinventory.presentation.base.BaseActivity
import com.imanfz.myinventory.presentation.friend.detail.adapter.FriendLoanAdapter
import com.imanfz.myinventory.utils.*
import com.imanfz.myinventory.viewmodel.AppViewModel
import com.imanfz.myinventory.viewmodel.ViewModelFactory

class DetailFriendActivity :
    BaseActivity<ActivityDetailFriendBinding>(
        ActivityDetailFriendBinding::inflate
    ) {

    private lateinit var appViewModel: AppViewModel
    private lateinit var friendLoanAdapter: FriendLoanAdapter
    private var friendId = 0
    private var friend: FriendEntity? = null
    private var isEdit = false
    private var imageByteArray: ByteArray? = null

    // handle result for image picker, because onActivityResult deprecated
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data

                    fileUri?.let {
                        binding.ivImage.loadImageFromUri(it)
                        imageByteArray = contentResolver.openInputStream(it)?.readBytes()
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "No selected", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setupObserver()
        setupListener()
        hideSoftKeyboard()
    }

    private fun init() {
        appViewModel = obtainViewModel(this)
        friendLoanAdapter = FriendLoanAdapter(object : FriendLoanAdapter.OnClickListener {
            override fun OnCLick(data: EquipmentLoanEntity) {
                showDialogConfirm(data)
            }
        })
        binding.rvEquipment.apply {
            layoutManager = LinearLayoutManager(this@DetailFriendActivity)
            setHasFixedSize(true)
            adapter = friendLoanAdapter
        }
        friendId = intent.getIntExtra(EXTRA_FRIEND, 0)
        disabled(true)
    }

    private fun setupObserver() {
        appViewModel.getAFriendById(friendId).observe(
            this, {
                if (it != null) {
                    friend = it
                    setupView()
                }
            }
        )
        appViewModel.getLoanByFriendId(friendId).observe(
            this, { list ->
                if (list.count() > 0) {
                    binding.rvEquipment.show()
                    binding.layoutEmpty.root.hide()
                    friendLoanAdapter.setListEquipmentLoan(list)
                } else {
                    binding.rvEquipment.hide()
                    binding.layoutEmpty.root.show()
                }
            }
        )
    }

    private fun setupView() {
        if (friendId != 0) {
            setupToolbar("Edit Friend", true, isEditEnabled = true, isDeleteEnabled = true)
            binding.apply {
                if (friend != null) {
                    friend?.let {
                        etFriendName.setText(it.name)
                        it.avatar?.let { it1 ->
                            ivImage.loadImageFromByteArray(it1)
                            imageByteArray = it1
                        }
                    }
                }
                btnSave.btnText.text = getString(R.string.update)
            }
        } else {
            setupToolbar("New Friend", true)
            friend = FriendEntity()
            disabled(false)
            binding.btnSave.btnText.text = getString(R.string.save)
        }
    }

    private fun setupListener() {
        binding.apply {
            toolbar.btnDelete.setOnClickListener {
                showAlertDialog()
            }

            toolbar.btnEdit.setOnClickListener {
                setupToolbar("Edit Item", true, isEditEnabled = true, isDeleteEnabled = true)
                isEdit = true
                disabled(false)
            }

            ivImage.setOnClickListener {
                ImageViewerDialog.apply {
                    if (imageByteArray != null) {
                        newInstance(null, imageByteArray, 1)
                            .show(supportFragmentManager, "")
                    }
                }
            }

            btnPickImage.setOnClickListener {
                ImagePicker.with(this@DetailFriendActivity)
                    .compress(3072)   //Final image size will be less than 3 MB(Optional)
                    .maxResultSize(
                        1080,
                        1920
                    )  //Final image resolution will be less than 1080 x 1920(Optional)
                    .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!) //  Path: /storage/sdcard0/Android/data/package/files/Pictures
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }
            }

            btnSave.btnPrimary.setOnClickListener {
                val name = etFriendName.text.toString()

                when {
                    name.isEmpty() -> tilPersonName.error = "Field can not be blank"
                    else -> {
                        friend?.let {
                            it.name = name
                            it.avatar = imageByteArray
                        }
                        if (isEdit) {
                            appViewModel.updateFriend(friend as FriendEntity)
                            showToast("Data has been changed")
                        } else {
                            appViewModel.insertFriend(friend as FriendEntity)
                            showToast("Successfully adding data")
                        }
                        finish()
                    }
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): AppViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AppViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog() {
        val dialogTitle = "Delete"
        val dialogMessage = "Are you sure to delete this friend?"
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Yes") { _, _ ->
                appViewModel.deleteFriend(friend as FriendEntity)
                showToast("Friend has been delete")
                finish()
            }
            setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun disabled(value: Boolean) {
        if (value) {
            binding.apply {
                btnPickImage.hide()
                etFriendName.isEnabled = false
                layoutEquipment.show()
                btnSave.root.hide()
            }
        } else {
            binding.apply {
                btnPickImage.show()
                etFriendName.isEnabled = true
                layoutEquipment.hide()
                btnSave.root.show()
            }
        }
    }

    private fun showDialogConfirm(data: EquipmentLoanEntity) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure this item returned ?")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                appViewModel.deleteLoan(data.loanEntity)
                appViewModel.updateEquipment(
                    EquipmentEntity(
                        id = data.equipment.id,
                        name = data.equipment.name,
                        image = data.equipment.image,
                        quantity = (data.equipment.quantity?.plus(data.loanEntity.count))
                    )
                )
                showToast("Success, equipment has been returned")
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}
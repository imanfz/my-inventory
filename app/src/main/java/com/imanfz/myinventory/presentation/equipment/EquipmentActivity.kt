package com.imanfz.myinventory.presentation.equipment

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.imanfz.myinventory.R
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.databinding.ActivityEquipmentBinding
import com.imanfz.myinventory.presentation.base.BaseActivity
import com.imanfz.myinventory.presentation.equipment.adapter.EquipmentLoanAdapter
import com.imanfz.myinventory.utils.*
import com.imanfz.myinventory.viewmodel.AppViewModel

class EquipmentActivity :
    BaseActivity<ActivityEquipmentBinding>(
        ActivityEquipmentBinding::inflate
) {

    private lateinit var appViewModel: AppViewModel
    private lateinit var equipmentLoanAdapter: EquipmentLoanAdapter
    private var equipmentId = 0
    private var equipment: EquipmentEntity? = null
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
        equipmentLoanAdapter = EquipmentLoanAdapter()
        binding.rvEquipment.apply {
            layoutManager = LinearLayoutManager(this@EquipmentActivity)
            setHasFixedSize(true)
            adapter = equipmentLoanAdapter
        }
        //equipment = intent.getParcelableExtra(EXTRA_EQUIPMENT)
        equipmentId = intent.getIntExtra(EXTRA_EQUIPMENT, 0)
        disabled(true)
    }

    private fun setupObserver() {
        appViewModel.getEquipmentById(equipmentId).observe(
            this, {
                if (it != null) {
                    equipment = it
                    setupView()
                }
            }
        )
        appViewModel.getLoanByEquipmentId(equipmentId).observe(
            this, { list ->
                if (list.count() > 0) {
                    binding.rvEquipment.show()
                    binding.layoutEmpty.root.hide()
                    equipmentLoanAdapter.setListEquipmentLoan(list)
                } else {
                    binding.rvEquipment.hide()
                    binding.layoutEmpty.root.show()
                }
            }
        )
    }

    private fun setupView() {
        binding.btnLoan.btnText.text = getString(R.string.loan)
        if (equipmentId != 0) {
            setupToolbar("Edit Item", true, isEditEnabled = true, isDeleteEnabled = true)
            binding.apply {
                if (equipment != null) {
                    equipment?.let {
                        etItemName.setText(it.name)
                        etItemCount.setText(it.quantity.toString())
                        if (it.quantity == 0) btnLoan.root.hide()
                        it.image?.let { it1 ->
                            ivImage.loadImageFromByteArray(it1)
                            imageByteArray = it1
                        }
                    }
                }
                btnSave.btnText.text = getString(R.string.update)
            }
        } else {
            setupToolbar("New Item", true)
            equipment = EquipmentEntity()
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
                ImagePicker.with(this@EquipmentActivity)
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

            btnLoan.btnPrimary.setOnClickListener {
                LoanDialogFragment.apply {
                    if (equipment != null) {
                        equipment?.let {
                            newInstance(it).show(supportFragmentManager, "")
                        }
                    }
                }
            }

            btnSave.btnPrimary.setOnClickListener {
                val name = etItemName.text.toString()
                val qty = etItemCount.text.toString()

                when {
                    name.isEmpty() -> tilItemName.error = "Field can not be blank"
                    qty.isEmpty() -> tilItemCount.error = "Field can not be blank"
                    (qty.toInt() == 0) -> tilItemCount.error = "Value cannot equals zero"
                    else -> {
                        equipment?.let {
                            it.name = name
                            it.quantity = qty.toInt()
                            it.image = imageByteArray
                        }
                        if (isEdit) {
                            appViewModel.updateEquipment(equipment as EquipmentEntity)
                            showToast("Data has been changed")
                        } else {
                            appViewModel.insertEquipment(equipment as EquipmentEntity)
                            showToast("Successfully adding data")
                        }
                        finish()
                    }
                }
            }


        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog() {
        val dialogTitle = "Delete"
        val dialogMessage = "Are you sure to delete this item?"
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Yes") { _, _ ->
                appViewModel.deleteEquipment(equipment as EquipmentEntity)
                showToast("Item has been delete")
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
                etItemName.isEnabled = false
                etItemCount.isEnabled = false
                layoutFriend.show()
                btnLoan.root.show()
                btnSave.root.hide()
            }
        } else {
            binding.apply {
                btnPickImage.show()
                etItemName.isEnabled = true
                etItemCount.isEnabled = true
                layoutFriend.hide()
                btnLoan.root.hide()
                btnSave.root.show()
            }
        }
    }
}
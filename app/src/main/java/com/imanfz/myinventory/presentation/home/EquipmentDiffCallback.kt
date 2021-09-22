package com.imanfz.myinventory.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.imanfz.myinventory.data.local.entity.EquipmentEntity

class EquipmentDiffCallback(
    private val mOldEquipmentList: List<EquipmentEntity>,
    private val mNewEquipmentList: List<EquipmentEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldEquipmentList.size
    }

    override fun getNewListSize(): Int {
        return mNewEquipmentList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEquipmentList[oldItemPosition].id == mNewEquipmentList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldEquipmentList[oldItemPosition]
        val newItem = mNewEquipmentList[newItemPosition]
        return oldItem.name == newItem.name && oldItem.quantity == newItem.quantity
    }

}
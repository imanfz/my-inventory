package com.imanfz.myinventory.presentation.friend.detail

import androidx.recyclerview.widget.DiffUtil
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity

class FriendLoanDiffCallback(
    private val mOldEquipmentList: List<EquipmentLoanEntity>,
    private val mNewEquipmentList: List<EquipmentLoanEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldEquipmentList.size
    }

    override fun getNewListSize(): Int {
        return mNewEquipmentList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEquipmentList[oldItemPosition].loanEntity.id == mNewEquipmentList[newItemPosition].loanEntity.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldEquipmentList[oldItemPosition]
        val newItem = mNewEquipmentList[newItemPosition]
        return oldItem.friend == newItem.friend &&
                oldItem.equipment == newItem.equipment &&
                oldItem.loanEntity.count == newItem.loanEntity.count
    }

}
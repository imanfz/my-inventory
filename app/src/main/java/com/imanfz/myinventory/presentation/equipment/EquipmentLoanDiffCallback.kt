package com.imanfz.myinventory.presentation.equipment

import androidx.recyclerview.widget.DiffUtil
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity
import com.imanfz.myinventory.data.local.entity.LoanEntity

class EquipmentLoanDiffCallback(
    private val mOldFriendList: List<EquipmentLoanEntity>,
    private val mNewFriendList: List<EquipmentLoanEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFriendList.size
    }

    override fun getNewListSize(): Int {
        return mNewFriendList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFriendList[oldItemPosition].loanEntity.id == mNewFriendList[newItemPosition].loanEntity.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldFriendList[oldItemPosition]
        val newItem = mNewFriendList[newItemPosition]
        return oldItem.friend == newItem.friend
    }

}
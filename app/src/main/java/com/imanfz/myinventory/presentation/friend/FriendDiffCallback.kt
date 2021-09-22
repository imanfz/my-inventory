package com.imanfz.myinventory.presentation.friend

import androidx.recyclerview.widget.DiffUtil
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity

class FriendDiffCallback(
    private val mOldFriendList: List<FriendEntity>,
    private val mNewFriendList: List<FriendEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFriendList.size
    }

    override fun getNewListSize(): Int {
        return mNewFriendList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFriendList[oldItemPosition].id == mNewFriendList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldFriendList[oldItemPosition]
        val newItem = mNewFriendList[newItemPosition]
        return oldItem.name == newItem.name
    }

}
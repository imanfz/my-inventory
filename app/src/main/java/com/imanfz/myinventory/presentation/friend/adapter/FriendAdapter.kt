package com.imanfz.myinventory.presentation.friend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imanfz.myinventory.data.local.entity.FriendEntity
import com.imanfz.myinventory.databinding.ItemRowFriendBinding
import com.imanfz.myinventory.presentation.friend.FriendDiffCallback
import com.imanfz.myinventory.presentation.friend.detail.DetailFriendActivity
import com.imanfz.myinventory.utils.EXTRA_FRIEND
import com.imanfz.myinventory.utils.loadImageFromByteArray
import org.jetbrains.anko.startActivity

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {
    private val listFriend = ArrayList<FriendEntity>()

    fun setListFriend(listFriend: List<FriendEntity>) {
        val diffCallback = FriendDiffCallback(this.listFriend, listFriend)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFriend.clear()
        this.listFriend.addAll(listFriend)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder =
        FriendViewHolder(ItemRowFriendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(listFriend[position])
    }

    override fun getItemCount(): Int = listFriend.size

    inner class FriendViewHolder(
        private val binding: ItemRowFriendBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: FriendEntity) {
            binding.apply {
                friend.avatar?.let { ivFriend.loadImageFromByteArray(it) }
                tvName.text = friend.name
                root.setOnClickListener {
                    it.context.startActivity<DetailFriendActivity>(EXTRA_FRIEND to friend.id)
                }
            }
        }
    }
}
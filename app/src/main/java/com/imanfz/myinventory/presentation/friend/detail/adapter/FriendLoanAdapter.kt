package com.imanfz.myinventory.presentation.friend.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity
import com.imanfz.myinventory.databinding.ItemRowEquipmentLoanBinding
import com.imanfz.myinventory.presentation.friend.detail.FriendLoanDiffCallback
import com.imanfz.myinventory.utils.loadImageFromByteArray

class FriendLoanAdapter(
    val listener : OnClickListener
) : RecyclerView.Adapter<FriendLoanAdapter.FriendLoanViewHolder>() {
    private val listEquipmentLoan = ArrayList<EquipmentLoanEntity>()

    fun setListEquipmentLoan(listEquipmentLoan: List<EquipmentLoanEntity>) {
        val diffCallback = FriendLoanDiffCallback(this.listEquipmentLoan, listEquipmentLoan)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listEquipmentLoan.clear()
        this.listEquipmentLoan.addAll(listEquipmentLoan)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendLoanViewHolder =
        FriendLoanViewHolder(
            ItemRowEquipmentLoanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FriendLoanViewHolder, position: Int) {
        holder.bind(listEquipmentLoan[position])
    }

    override fun getItemCount(): Int = listEquipmentLoan.size

    inner class FriendLoanViewHolder(
        private val binding: ItemRowEquipmentLoanBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loan: EquipmentLoanEntity) {
            binding.apply {
                loan.equipment.image?.let { ivFriend.loadImageFromByteArray(it) }
                tvName.text = loan.equipment.name
                tvCount.text = "Qty: ${loan.loanEntity.count}"
                root.setOnClickListener {
                    listener.OnCLick(loan)
                }
            }
        }
    }

    interface OnClickListener {
        fun OnCLick(data: EquipmentLoanEntity)
    }
}
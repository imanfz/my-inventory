package com.imanfz.myinventory.presentation.equipment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imanfz.myinventory.R
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity
import com.imanfz.myinventory.databinding.ItemRowEquipmentLoanBinding
import com.imanfz.myinventory.presentation.equipment.EquipmentLoanDiffCallback
import com.imanfz.myinventory.utils.loadCircleImageFromByteArray

class EquipmentLoanAdapter : RecyclerView.Adapter<EquipmentLoanAdapter.EquipmentLoanViewHolder>() {
    private val listEquipmentLoan = ArrayList<EquipmentLoanEntity>()

    fun setListEquipmentLoan(listEquipmentLoan: List<EquipmentLoanEntity>) {
        val diffCallback = EquipmentLoanDiffCallback(this.listEquipmentLoan, listEquipmentLoan)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listEquipmentLoan.clear()
        this.listEquipmentLoan.addAll(listEquipmentLoan)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentLoanViewHolder =
        EquipmentLoanViewHolder(
            ItemRowEquipmentLoanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EquipmentLoanViewHolder, position: Int) {
        holder.bind(listEquipmentLoan[position])
    }

    override fun getItemCount(): Int = listEquipmentLoan.size

    inner class EquipmentLoanViewHolder(
        private val binding: ItemRowEquipmentLoanBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loan: EquipmentLoanEntity) {
            binding.apply {
                loan.friend.avatar?.let { ivFriend.loadCircleImageFromByteArray(it) }
                tvName.text = loan.friend.name
                tvCount.text = binding.root.context.getString(
                    R.string.qty,
                    loan.loanEntity.count
                )
            }
        }
    }
}
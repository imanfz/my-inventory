package com.imanfz.myinventory.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.databinding.ItemRowEquipmentBinding
import com.imanfz.myinventory.presentation.equipment.EquipmentActivity
import com.imanfz.myinventory.presentation.equipment.EquipmentActivity.Companion.EXTRA_EQUIPMENT
import com.imanfz.myinventory.presentation.home.EquipmentDiffCallback
import com.imanfz.myinventory.utils.loadImageFromByteArray
import org.jetbrains.anko.startActivity

class EquipmentAdapter : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {
    private val listEquipment = ArrayList<EquipmentEntity>()

    fun setListEquipment(listEquipment: List<EquipmentEntity>) {
        val diffCallback = EquipmentDiffCallback(this.listEquipment, listEquipment)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listEquipment.clear()
        this.listEquipment.addAll(listEquipment)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder =
        EquipmentViewHolder(ItemRowEquipmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.bind(listEquipment[position])
    }

    override fun getItemCount(): Int = listEquipment.size

    inner class EquipmentViewHolder(
        private val binding: ItemRowEquipmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(equipment: EquipmentEntity) {
            binding.apply {
                equipment.image?.let { ivEquipment.loadImageFromByteArray(it) }
                tvName.text = equipment.name
                tvStock.text = "Stock: ${equipment.quantity}"
                root.setOnClickListener {
                    it.context.startActivity<EquipmentActivity>(EXTRA_EQUIPMENT to equipment)
                }
            }
        }
    }
}
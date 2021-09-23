package com.imanfz.myinventory.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imanfz.myinventory.R
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.databinding.ItemRowEquipmentBinding
import com.imanfz.myinventory.presentation.equipment.EquipmentActivity
import com.imanfz.myinventory.presentation.home.EquipmentDiffCallback
import com.imanfz.myinventory.utils.EXTRA_EQUIPMENT
import com.imanfz.myinventory.utils.loadCircleImageFromByteArray
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
                if (equipment.quantity == 0) {
                    val layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0
                    )
                    root.layoutParams = layoutParams
                }
                equipment.image?.let { ivEquipment.loadCircleImageFromByteArray(it) }
                tvName.text = equipment.name
                tvStock.text = binding.root.context.getString(
                    R.string.qty,
                    equipment.quantity
                )
                root.setOnClickListener {
                    it.context.startActivity<EquipmentActivity>(EXTRA_EQUIPMENT to equipment.id)
                }
            }
        }
    }
}
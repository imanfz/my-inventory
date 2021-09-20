package com.imanfz.myinventory.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_equipment")
@Parcelize
data class EquipmentEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var name: String? = null,

    @ColumnInfo
    var quantity: Int? = null
) : Parcelable
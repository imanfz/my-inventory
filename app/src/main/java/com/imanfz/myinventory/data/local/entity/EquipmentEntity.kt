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
    var quantity: Int? = null,

    @ColumnInfo
    var image: ByteArray? = null
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EquipmentEntity

        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        return image?.contentHashCode() ?: 0
    }
}
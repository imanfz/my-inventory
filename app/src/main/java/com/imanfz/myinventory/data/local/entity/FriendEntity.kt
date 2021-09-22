package com.imanfz.myinventory.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_friend")
@Parcelize
data class FriendEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var name: String? = null,

    @ColumnInfo
    var avatar: ByteArray? = null
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendEntity

        if (avatar != null) {
            if (other.avatar == null) return false
            if (!avatar.contentEquals(other.avatar)) return false
        } else if (other.avatar != null) return false

        return true
    }

    override fun hashCode(): Int {
        return avatar?.contentHashCode() ?: 0
    }
}
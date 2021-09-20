package com.imanfz.myinventory.data.local.entity

import android.graphics.Bitmap
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
    var avatar: Bitmap? = null
) : Parcelable
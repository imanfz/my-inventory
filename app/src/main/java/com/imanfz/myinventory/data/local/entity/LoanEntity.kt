package com.imanfz.myinventory.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_loan")
@Parcelize
data class LoanEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var friendId: Int,

    @ColumnInfo
    var equipmentId: Int,

    @ColumnInfo
    var count: Int
) : Parcelable
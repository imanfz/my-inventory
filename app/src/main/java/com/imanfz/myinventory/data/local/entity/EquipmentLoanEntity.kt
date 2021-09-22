package com.imanfz.myinventory.data.local.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class EquipmentLoanEntity(
    @Embedded val loanEntity: LoanEntity,

    @Relation(
        parentColumn = "equipmentId",
        entityColumn = "id"
    )
    val equipment: EquipmentEntity,

    @Relation(
        parentColumn = "friendId",
        entityColumn = "id"
    )
    val friend: FriendEntity,
) : Parcelable
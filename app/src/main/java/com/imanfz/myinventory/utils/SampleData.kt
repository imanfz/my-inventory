package com.imanfz.myinventory.utils

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity

object SampleData {
    val sampleEquipment = arrayListOf(
        EquipmentEntity(
            name = "Wrench",
            image = null,
            quantity = 6
        ),
        EquipmentEntity(
            name = "Cutter",
            image = null,
            quantity = 15
        ),
        EquipmentEntity(
            name = "Pliers",
            image = null,
            quantity = 12
        ),
        EquipmentEntity(
            name = "Screwdriver",
            image = null,
            quantity = 13
        ),
        EquipmentEntity(
            name = "Welding glasses",
            image = null,
            quantity = 7
        ),
        EquipmentEntity(
            name = "Hammer",
            image = null,
            quantity = 4
        ),
        EquipmentEntity(
            name = "Measuring Tape",
            image = null,
            quantity = 9
        ),
        EquipmentEntity(
            name = "Alan key set",
            image = null,
            quantity = 4
        ),
        EquipmentEntity(
            name = "Air compressor",
            image = null,
            quantity = 2
        )
    )

    val sampleFriend = arrayListOf(
        FriendEntity(
            name = "Brian",
            avatar = null
        ),
        FriendEntity(
            name = "Luke",
            avatar = null
        ),
        FriendEntity(
            name = "Letty",
            avatar = null
        ),
        FriendEntity(
            name = "Shaw",
            avatar = null
        ),
        FriendEntity(
            name = "Parker",
            avatar = null
        )
    )
}
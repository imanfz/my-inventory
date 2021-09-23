package com.imanfz.myinventory.utils

import android.content.Context
import com.imanfz.myinventory.R
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity

class SampleData(context: Context) {

    val sampleEquipment = arrayListOf(
        EquipmentEntity(
            name = "Wrench",
            image = context.convertToByteArray(R.drawable.wrench),
            quantity = 6
        ),
        EquipmentEntity(
            name = "Cutter",
            image = context.convertToByteArray(R.drawable.cutter),
            quantity = 15
        ),
        EquipmentEntity(
            name = "Pliers",
            image = context.convertToByteArray(R.drawable.pliers),
            quantity = 12
        ),
        EquipmentEntity(
            name = "Screwdriver",
            image = context.convertToByteArray(R.drawable.screwdriver),
            quantity = 13
        ),
        EquipmentEntity(
            name = "Welding machine",
            image = context.convertToByteArray(R.drawable.welding_machine),
            quantity = 3
        ),
        EquipmentEntity(
            name = "Welding glasses",
            image =  context.convertToByteArray(R.drawable.welding_glasses),
            quantity = 7
        ),
        EquipmentEntity(
            name = "Hammer",
            image = context.convertToByteArray(R.drawable.hammer),
            quantity = 4
        ),
        EquipmentEntity(
            name = "Measuring Tape",
            image = context.convertToByteArray(R.drawable.tape),
            quantity = 9
        ),
        EquipmentEntity(
            name = "Alan key set",
            image =  context.convertToByteArray(R.drawable.alan_key),
            quantity = 4
        ),
        EquipmentEntity(
            name = "Air compressor",
            image =  context.convertToByteArray(R.drawable.air_compressor),
            quantity = 2
        )
    )

    val sampleFriend = arrayListOf(
        FriendEntity(
            name = "Brian",
            avatar = context.convertToByteArray(R.drawable.brian)
        ),
        FriendEntity(
            name = "Luke",
            avatar = context.convertToByteArray(R.drawable.luke)
        ),
        FriendEntity(
            name = "Letty",
            avatar = context.convertToByteArray(R.drawable.letty)
        ),
        FriendEntity(
            name = "Shaw",
            avatar = context.convertToByteArray(R.drawable.shaw)
        ),
        FriendEntity(
            name = "Parker",
            avatar = context.convertToByteArray(R.drawable.parker)
        )
    )
}
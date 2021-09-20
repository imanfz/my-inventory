package com.imanfz.myinventory.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import io.reactivex.Single

@Dao
interface EquipmentDao: BaseDao<EquipmentEntity> {

    @Query("SELECT * FROM tb_equipment")
    fun getAll(): LiveData<List<EquipmentEntity>>

    @Query("SELECT * FROM tb_equipment WHERE id = :mId")
    fun getById(mId: Int): EquipmentEntity

}
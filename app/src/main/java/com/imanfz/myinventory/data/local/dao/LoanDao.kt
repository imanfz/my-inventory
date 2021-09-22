package com.imanfz.myinventory.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity
import com.imanfz.myinventory.data.local.entity.LoanEntity

@Dao
interface LoanDao: BaseDao<LoanEntity> {

    @Query("SELECT * FROM tb_loan")
    fun getAll(): LiveData<List<LoanEntity>>

    @Query("SELECT * FROM tb_loan WHERE id = :mId")
    fun getById(mId: Int): LoanEntity

    @Transaction
    @Query("SELECT * FROM tb_loan WHERE equipmentId = :eId")
    fun getByEquipmentId(eId: Int): LiveData<List<EquipmentLoanEntity>>

    @Transaction
    @Query("SELECT * FROM tb_loan WHERE friendId = :fId")
    fun getByFriendId(fId: Int): LiveData<List<EquipmentLoanEntity>>
}
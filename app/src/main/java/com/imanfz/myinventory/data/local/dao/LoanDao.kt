package com.imanfz.myinventory.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.imanfz.myinventory.data.local.entity.LoanEntity
import io.reactivex.Single

@Dao
interface LoanDao: BaseDao<LoanEntity> {

    @Query("SELECT * FROM tb_loan")
    fun getAll(): LiveData<List<LoanEntity>>

    @Query("SELECT * FROM tb_loan WHERE id = :mId")
    fun getById(mId: Int): LoanEntity

}
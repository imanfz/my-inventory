package com.imanfz.myinventory.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.imanfz.myinventory.data.local.entity.FriendEntity
import io.reactivex.Single

@Dao
interface FriendDao: BaseDao<FriendEntity> {

    @Query("SELECT * FROM tb_friend")
    fun getAll(): LiveData<List<FriendEntity>>

    @Query("SELECT * FROM tb_friend WHERE id = :mId")
    fun getById(mId: Int): FriendEntity

}
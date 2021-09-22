package com.imanfz.myinventory.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.imanfz.myinventory.data.local.dao.EquipmentDao
import com.imanfz.myinventory.data.local.dao.FriendDao
import com.imanfz.myinventory.data.local.dao.LoanDao
import com.imanfz.myinventory.data.local.database.AppDatabase
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity
import com.imanfz.myinventory.data.local.entity.LoanEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AppRepository(
    application: Application
) {
    private val equipmentDao: EquipmentDao
    private val friendDao: FriendDao
    private val loanDao: LoanDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = AppDatabase.getDatabase(application)
        equipmentDao = db.equipmentDao()
        friendDao = db.friendDao()
        loanDao = db.loanDao()
    }

    // Equipment
    fun insertEquipment(data: EquipmentEntity) {
        executorService.execute{ equipmentDao.insert(data) }
    }

    fun updateEquipment(data: EquipmentEntity) {
        executorService.execute { equipmentDao.update(data) }
    }

    fun deleteEquipment(data: EquipmentEntity) {
        executorService.execute { equipmentDao.delete(data) }
    }

    fun getAllEquipment(): LiveData<List<EquipmentEntity>> = equipmentDao.getAll()

    fun getEquipmentById(id: Int): EquipmentEntity = equipmentDao.getById(id)

    // Friend
    fun insertFriend(data: FriendEntity) {
        executorService.execute{ friendDao.insert(data) }
    }

    fun updateFriend(data: FriendEntity) {
        executorService.execute { friendDao.update(data) }
    }

    fun deleteFriend(data: FriendEntity) {
        executorService.execute { friendDao.delete(data) }
    }

    fun getAllFriend(): LiveData<List<FriendEntity>> = friendDao.getAll()

    fun getFriendById(id: Int): FriendEntity = friendDao.getById(id)

    // Loan
    fun insertLoan(data: LoanEntity) {
        executorService.execute{ loanDao.insert(data) }
    }

    fun updateLoan(data: LoanEntity) {
        executorService.execute { loanDao.update(data) }
    }

    fun deleteLoan(data: LoanEntity) {
        executorService.execute { loanDao.delete(data) }
    }

    fun getLoanByEquipmentId(id: Int): LiveData<List<EquipmentLoanEntity>> = loanDao.getByEquipmentId(id)

    fun getLoanByFriendId(id: Int): LiveData<List<EquipmentLoanEntity>> = loanDao.getByFriendId(id)

}
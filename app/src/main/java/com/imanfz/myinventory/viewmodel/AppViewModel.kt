package com.imanfz.myinventory.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.EquipmentLoanEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity
import com.imanfz.myinventory.data.local.entity.LoanEntity
import com.imanfz.myinventory.data.repository.AppRepository

class AppViewModel(
    application: Application
): ViewModel() {
    private val appRepository = AppRepository(application)

    // Equipment
    fun getAllEquipment() : LiveData<List<EquipmentEntity>> =
        appRepository.getAllEquipment()

    fun insertEquipment(data: EquipmentEntity) {
        appRepository.insertEquipment(data)
    }

    fun updateEquipment(data: EquipmentEntity) {
        appRepository.updateEquipment(data)
    }

    fun deleteEquipment(data: EquipmentEntity) {
        appRepository.deleteEquipment(data)
    }

    // Friend
    fun getAllFriend() : LiveData<List<FriendEntity>> =
        appRepository.getAllFriend()

    fun insertFriend(data: FriendEntity) {
        appRepository.insertFriend(data)
    }

    fun updateFriend(data: FriendEntity) {
        appRepository.updateFriend(data)
    }

    fun deleteFriend(data: FriendEntity) {
        appRepository.deleteFriend(data)
    }

    // Loan

    fun insertLoan(data: LoanEntity) {
        appRepository.insertLoan(data)
    }

    fun updateLoan(data: LoanEntity) {
        appRepository.updateLoan(data)
    }

    fun deleteLoan(data: LoanEntity) {
        appRepository.deleteLoan(data)
    }

    fun getLoanByEquipmentId(id: Int) : LiveData<List<EquipmentLoanEntity>> =
        appRepository.getLoanByEquipmentId(id)

    fun getLoanByFriendId(id: Int) : LiveData<List<EquipmentLoanEntity>> =
        appRepository.getLoanByFriendId(id)

}
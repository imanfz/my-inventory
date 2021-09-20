package com.imanfz.myinventory.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imanfz.myinventory.data.local.dao.EquipmentDao
import com.imanfz.myinventory.data.local.dao.FriendDao
import com.imanfz.myinventory.data.local.dao.LoanDao
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity
import com.imanfz.myinventory.data.local.entity.LoanEntity

@Database(
    entities = [
        EquipmentEntity::class,
        FriendEntity::class,
        LoanEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun equipmentDao(): EquipmentDao
    abstract fun friendDao(): FriendDao
    abstract fun loanDao(): LoanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    ).build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}
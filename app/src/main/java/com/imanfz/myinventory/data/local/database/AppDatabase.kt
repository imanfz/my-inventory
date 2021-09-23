package com.imanfz.myinventory.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imanfz.myinventory.data.local.dao.EquipmentDao
import com.imanfz.myinventory.data.local.dao.FriendDao
import com.imanfz.myinventory.data.local.dao.LoanDao
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity
import com.imanfz.myinventory.data.local.entity.LoanEntity
import com.imanfz.myinventory.utils.SampleData
import java.util.concurrent.Executors

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
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            // insert sample data on first create db
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let {
                                    it.equipmentDao().insert(SampleData.sampleEquipment)
                                    it.friendDao().insert(SampleData.sampleFriend)
                                }
                            }
                        }
                    }) .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}
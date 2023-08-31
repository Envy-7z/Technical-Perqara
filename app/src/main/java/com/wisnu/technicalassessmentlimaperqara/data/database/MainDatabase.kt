package com.wisnu.technicalassessmentlimaperqara.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wisnu.technicalassessmentlimaperqara.data.models.FavEntity

@Database(entities = [FavEntity::class], version = 1)

abstract class MainDatabase : RoomDatabase() {
    abstract fun userDao(): MainDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MainDatabase {
            if (INSTANCE == null) {
                synchronized(MainDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java, "db_main"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as MainDatabase
        }
    }
}
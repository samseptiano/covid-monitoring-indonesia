package com.samseptiano.hcroadmap.Room.EmpList

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Database annotation to specify the entities and set version
@Database(entities = [DataRoom::class], version = 1, exportSchema = false)
abstract class DataRoomDatabase : RoomDatabase() {


    companion object {
        @Volatile
        private var INSTANCE: DataRoomDatabase? = null

        val DB_NAME:String = "data_db"

        fun getDatabase(context: Context): DataRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataRoomDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries() //allows Room to executing task in main thread
                    .fallbackToDestructiveMigration() //allows Room to recreate database if no migrations found
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getData() : DataDao
}
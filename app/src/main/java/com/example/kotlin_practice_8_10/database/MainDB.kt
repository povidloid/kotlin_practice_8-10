package com.example.kotlin_practice_8_10.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserDB::class], version = 1)
abstract class MainDB : RoomDatabase() {
    abstract fun getDao(): Dao
    companion object {
        fun getDb(context: Context): MainDB{
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "test.db"
            ).build()
        }
    }
}
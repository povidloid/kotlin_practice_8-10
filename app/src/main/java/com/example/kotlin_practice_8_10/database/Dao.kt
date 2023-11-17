package com.example.kotlin_practice_8_10.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {
    @Insert
    fun insertUser(userDB: UserDB)
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserDB>>
    @Query("DELETE FROM users WHERE id = :userId")
    fun deleteUserById(userId: Int)
    @Query("DELETE FROM users")
    fun deleteAllUsers()
}
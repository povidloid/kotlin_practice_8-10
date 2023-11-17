package com.example.kotlin_practice_8_10.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int ?= null,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "email")
    val email: String
)
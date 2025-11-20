package com.example.geo_moba.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.geo_moba.model.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    suspend fun emailExists(email: String): Boolean

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>
}

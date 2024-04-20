package com.example.github_api.model.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from user ORDER BY id ASC")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * from user WHERE id = :id")
    fun getUserById(id: Int): LiveData<User>
}
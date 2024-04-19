package com.example.github_api.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.github_api.model.local.User
import com.example.github_api.model.local.UserDao
import com.example.github_api.model.local.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUser(): LiveData<List<User>> = mUserDao.getAllUser()

    fun insert(user: User) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun delete(user: User) {
        executorService.execute { mUserDao.delete(user) }
    }

    fun update(user: User) {
        executorService.execute { mUserDao.update(user) }
    }
}
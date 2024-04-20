package com.example.github_api.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.local.User
import com.example.github_api.model.repository.FavoriteRepository

class FavouriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository = FavoriteRepository(application)

    fun getAllUser() : LiveData<List<User>> = mUserRepository.getAllUser()

    fun insert(user: User) : Unit = mUserRepository.insert(user)

    fun delete(user: User) : Unit = mUserRepository.delete(user)

    fun getUserById(id: Int) : LiveData<User> = mUserRepository.getUser(id)



}
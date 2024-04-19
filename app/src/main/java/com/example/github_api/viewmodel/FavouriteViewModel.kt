package com.example.github_api.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.local.User
import com.example.github_api.model.repository.UserRepository

class FavouriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository = UserRepository(application)

    fun getAllUser() : LiveData<List<User>> = mUserRepository.getAllUser()


}
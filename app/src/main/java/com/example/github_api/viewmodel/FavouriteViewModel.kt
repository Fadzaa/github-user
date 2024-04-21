package com.example.github_api.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.local.User
import com.example.github_api.model.repository.FavoriteRepository

class FavouriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository = FavoriteRepository(application)

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isDataEmpty = MutableLiveData<Boolean>()
    val isDataEmpty: LiveData<Boolean> = _isDataEmpty

    init {
        getAllUser()
    }
    private fun getAllUser() {
        mUserRepository.getAllUser().observeForever {
            _listUser.value = it
            _isDataEmpty.value = it.isEmpty()
        }
    }

    fun insert(user: User) : Unit = mUserRepository.insert(user)

    fun delete(user: User) : Unit = mUserRepository.delete(user)

    fun getUserById(id: Int) : LiveData<User> = mUserRepository.getUser(id)



}
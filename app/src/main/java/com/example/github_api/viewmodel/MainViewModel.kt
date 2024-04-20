package com.example.github_api.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.model.repository.MainRepository

class MainViewModel(apiService: ApiService, application: Application) : ViewModel() {
    private val mainRepository = MainRepository(apiService, application)

    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getMyUsers()
    }

    private fun getMyUsers() {
        _isLoading.value = true
        mainRepository.getMyUserDetail().observeForever {
            _user.value = it
            _isLoading.value = false
        }
    }
}
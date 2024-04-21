package com.example.github_api.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.repository.UserRepository

class SearchViewModel(apiService: ApiService, application: Application) : ViewModel() {
    private val userRepository = UserRepository(apiService, application)

    private val _listDetailUsers = MutableLiveData<List<DetailUserResponse>>(emptyList())
    val listDetailUsers: LiveData<List<DetailUserResponse>> = _listDetailUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDataEmpty = MutableLiveData<Boolean>()
    val isDataEmpty: LiveData<Boolean> = _isDataEmpty

    init {
        searchUsers("a")
    }

    fun searchUsers(query: String) {
        _listDetailUsers.value = emptyList()
        _isDataEmpty.value = false
        _isLoading.value = true

        userRepository.searchUsers(query).observeForever {
            _listDetailUsers.value = it
            _isLoading.value = false
            _isDataEmpty.value = it.isEmpty()
        }
    }
}

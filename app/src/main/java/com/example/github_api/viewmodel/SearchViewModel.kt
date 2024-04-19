package com.example.github_api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.repository.UserRepository

class SearchViewModel(apiService: ApiService) : ViewModel() {
    private val userRepository = UserRepository(apiService)

    private val _listDetailUsers = MutableLiveData<List<DetailUserResponse>>(emptyList())
    val listDetailUsers: LiveData<List<DetailUserResponse>> = _listDetailUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchUsers("a")
    }

    fun searchUsers(query: String) {
        _listDetailUsers.value = emptyList()
        _isLoading.value = true

        userRepository.searchUsers(query).observeForever {
            _listDetailUsers.value = it
            _isLoading.value = false
        }
    }
}

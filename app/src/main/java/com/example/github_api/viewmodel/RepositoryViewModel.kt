package com.example.github_api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.remote.response.RepositoryUserResponseItem
import com.example.github_api.model.remote.ApiConfig
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.repository.UserRepoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(username: String, apiService: ApiService) : ViewModel() {
    private val userRepoRepository = UserRepoRepository(apiService)

    private val _listRepository = MutableLiveData<List<RepositoryUserResponseItem>>(emptyList())
    val listRepository : LiveData<List<RepositoryUserResponseItem>> = _listRepository

    private val _listStarredRepository = MutableLiveData<List<RepositoryUserResponseItem>>(emptyList())
    val listStarredRepository : LiveData<List<RepositoryUserResponseItem>> = _listStarredRepository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        getListRepository(username)
        getListStarredRepository(username)
    }

    private fun getListRepository(username: String) {
        _isLoading.value = true
        userRepoRepository.getPublicRepo(username).observeForever {
            _isLoading.value = false
            _listRepository.value = it
        }
    }

    private fun getListStarredRepository(username: String) {
        _isLoading.value = true
        userRepoRepository.getStarredRepo(username).observeForever {
            _isLoading.value = false
            _listStarredRepository.value = it
        }
    }

}
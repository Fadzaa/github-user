package com.example.github_api.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.model.remote.response.RepositoryUserResponseItem
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.repository.UserRepoRepository

class RepositoryViewModel(username: String, apiService: ApiService, application: Application) : ViewModel() {
    private val userRepoRepository = UserRepoRepository(apiService, application)

    private val _listRepository = MutableLiveData<List<RepositoryUserResponseItem>>(emptyList())
    val listRepository : LiveData<List<RepositoryUserResponseItem>> = _listRepository

    private val _listStarredRepository = MutableLiveData<List<RepositoryUserResponseItem>>(emptyList())
    val listStarredRepository : LiveData<List<RepositoryUserResponseItem>> = _listStarredRepository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isRepoEmpty = MutableLiveData<Boolean>()
    val isRepoEmpty : LiveData<Boolean> = _isRepoEmpty

    private val _isStarredRepoEmpty = MutableLiveData<Boolean>()
    val isStarredRepoEmpty : LiveData<Boolean> = _isStarredRepoEmpty

    init {
        getListRepository(username)
        getListStarredRepository(username)
    }

    private fun getListRepository(username: String) {
        _isLoading.value = true
        userRepoRepository.getPublicRepo(username).observeForever {
            _isLoading.value = false
            _listRepository.value = it
            _isRepoEmpty.value = it.isEmpty()
        }
    }

    private fun getListStarredRepository(username: String) {
        _isLoading.value = true
        userRepoRepository.getStarredRepo(username).observeForever {
            _isLoading.value = false
            _listStarredRepository.value = it
            _isStarredRepoEmpty.value = it.isEmpty()
        }
    }

}
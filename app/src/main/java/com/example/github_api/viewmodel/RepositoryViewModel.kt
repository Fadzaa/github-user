package com.example.github_api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.data.response.RepositoryUserResponseItem
import com.example.github_api.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(username: String) : ViewModel() {
    private val _listRepository = MutableLiveData<List<RepositoryUserResponseItem>>(emptyList())
    val listRepository : LiveData<List<RepositoryUserResponseItem>> = _listRepository

    private val _listStarredRepository = MutableLiveData<List<RepositoryUserResponseItem>>(emptyList())
    val listStarredRepository : LiveData<List<RepositoryUserResponseItem>> = _listStarredRepository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "RepositoryViewModel"
    }

    init {
        getListRepository(username)
        getListStarredRepository(username)
    }

    private fun getListRepository(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserRepository(username)

        client.enqueue(
            object : Callback<List<RepositoryUserResponseItem>> {
                override fun onResponse(call: Call<List<RepositoryUserResponseItem>>, response: Response<List<RepositoryUserResponseItem>>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listRepository.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<RepositoryUserResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )
    }

    private fun getListStarredRepository(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserStarredRepository(username)

        client.enqueue(
            object : Callback<List<RepositoryUserResponseItem>> {
                override fun onResponse(call: Call<List<RepositoryUserResponseItem>>, response: Response<List<RepositoryUserResponseItem>>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listStarredRepository  .value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<RepositoryUserResponseItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )
    }

}
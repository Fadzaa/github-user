package com.example.github_api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.data.response.ItemsItem
import com.example.github_api.data.response.SearchResponse
import com.example.github_api.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: LiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"

    }

    init {
        searchUsers("q")
        getMyUsers()
    }


    private fun searchUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUsers(query)

        client.enqueue(
            object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listUsers.value = response.body()?.items
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            }
        )
    }

    private fun getMyUsers() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getMyUserDetail()

        client.enqueue(
            object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _user.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            }
        )
    }
}
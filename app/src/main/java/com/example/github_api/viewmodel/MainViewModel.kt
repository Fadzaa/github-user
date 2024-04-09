package com.example.github_api.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.data.response.ItemsItem
import com.example.github_api.data.response.SearchResponse
import com.example.github_api.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: MutableLiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"

    }

    init {
        searchUsers("q")
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
}
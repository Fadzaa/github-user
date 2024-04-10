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

class SearchViewModel : ViewModel() {
    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: LiveData<List<ItemsItem>> = _listUsers

    private val _listDetailUsers = MutableLiveData<List<DetailUserResponse>>(emptyList())
    val listDetailUsers: LiveData<List<DetailUserResponse>> = _listDetailUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "SearchViewModel"
    }

    init {
        searchUsers("a")
    }

    private fun searchUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUsers(query)

        client.enqueue(
            object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    if (response.isSuccessful) {
                        val users = response.body()?.items

                        if (users != null) {
                            for (user in users) {
                                getDetailUser(user.login)
                            }

                        }
                        _isLoading.value = false
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

    private fun getDetailUser(username: String) {

        val client = ApiConfig.getApiService().getUserDetail(username)

        client.enqueue(
            object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    if (response.isSuccessful) {
                        val userDetail = response.body()
                        Log.d(TAG, "onResponse: $userDetail")
                        if (userDetail != null) {
                            _listDetailUsers.value = _listDetailUsers.value?.plus(userDetail)
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            }
        )
    }
}

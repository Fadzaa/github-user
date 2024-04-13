package com.example.github_api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.data.response.SearchResponse
import com.example.github_api.data.retrofit.ApiConfig
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _listDetailUsers = MutableLiveData<List<DetailUserResponse>>(emptyList())
    val listDetailUsers: LiveData<List<DetailUserResponse>> = _listDetailUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    companion object {
        private const val TAG = "SearchViewModel"
    }

    init {
        searchUsers("a")
    }

    fun searchUsers(query: String) {
        _listDetailUsers.value = emptyList()
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUsers(query)

        client.enqueue(
            object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    if (response.isSuccessful) {
                        val users = response.body()?.items
                        Log.d(TAG, "onResponse Users: $users")
                        if (users != null) {
                            coroutineScope.launch {
                                val userDetails = users.map { user ->
                                    async(Dispatchers.IO) { getDetailUser(user.login) }
                                }.awaitAll()

                                _listDetailUsers.value = userDetails.filterNotNull()
                                _isLoading.value = false
                            }
                        }
                    } else {
                        _isLoading.value = false
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

    private fun getDetailUser(username: String): DetailUserResponse? {

        val client = ApiConfig.getApiService().getUserDetail(username)

        return try {
            val response = client.execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e(TAG, "onFailure: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "onFailure: ${e.message}")
            null
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

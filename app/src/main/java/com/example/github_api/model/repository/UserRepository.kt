package com.example.github_api.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.model.remote.response.ListFollowResponseItem
import com.example.github_api.model.remote.response.SearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    fun getListFollowers(username: String): LiveData<List<DetailUserResponse>> {
        val client = apiService.getUserFollowers(username)
        val data = MutableLiveData<List<DetailUserResponse>>()

        client.enqueue(
            object : Callback<List<ListFollowResponseItem>> {
                override fun onResponse(call: Call<List<ListFollowResponseItem>>, response: Response<List<ListFollowResponseItem>>) {
                    if (response.isSuccessful) {
                        val listFollowers  = response.body()
                        if (listFollowers != null) {
                            coroutineScope.launch {
                                val userDetails = listFollowers.map { follow ->
                                    async(Dispatchers.IO) { getDetailUser(follow.login) }
                                }.awaitAll()

                                data.value = userDetails.filterNotNull()
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ListFollowResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return data
    }

    fun getListFollowings(username: String): LiveData<List<DetailUserResponse>> {
        val client = apiService.getUserFollowings(username)
        val data = MutableLiveData<List<DetailUserResponse>>()

        client.enqueue(
            object : Callback<List<ListFollowResponseItem>> {
                override fun onResponse(call: Call<List<ListFollowResponseItem>>, response: Response<List<ListFollowResponseItem>>) {
                    if (response.isSuccessful) {
                        val listFollowings = response.body()
                        if (listFollowings != null) {
                            coroutineScope.launch {
                                val userDetails = listFollowings.map { follow ->
                                    async(Dispatchers.IO) { getDetailUser(follow.login) }
                                }.awaitAll()
                                data.value = userDetails.filterNotNull()
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ListFollowResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return data
    }

    fun searchUsers(query: String): LiveData<List<DetailUserResponse>> {
        val data = MutableLiveData<List<DetailUserResponse>>()
        val client = apiService.searchUsers(query)

        client.enqueue(
            object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    if (response.isSuccessful) {
                        val users = response.body()?.items
                        if (users != null) {
                            coroutineScope.launch {
                                val userDetails = users.map { user ->
                                    async(Dispatchers.IO) { getDetailUser(user.login) }
                                }.awaitAll()
                                data.value = userDetails.filterNotNull()
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return data
    }

    fun getDetailUser(username: String): DetailUserResponse? {
        val client = apiService.getUserDetail(username)
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

    companion object {
        private const val TAG = "DetailRepository"
    }
}
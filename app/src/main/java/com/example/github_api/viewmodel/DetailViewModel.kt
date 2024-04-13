package com.example.github_api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.data.response.ListFollowResponseItem
import com.example.github_api.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (username: String) : ViewModel() {
    private val _listDetailFollowers = MutableLiveData<List<DetailUserResponse>>(emptyList())
    val listDetailFollowers: LiveData<List<DetailUserResponse>> = _listDetailFollowers

    private val _listDetailFollowings = MutableLiveData<List<DetailUserResponse>>(emptyList())
    val listDetailFollowings: LiveData<List<DetailUserResponse>> = _listDetailFollowings

    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    companion object{
        private const val TAG = "DetailViewModel"
    }

    init {
        getListFollowers(username)
        getListFollowings(username)
    }

    private fun getListFollowers(username: String) {
        _isLoadingFollowers.value = true
        val client =  ApiConfig.getApiService().getUserFollowers(username)

        client.enqueue(
            object : Callback<List<ListFollowResponseItem>> {
                override fun onResponse(call: Call<List<ListFollowResponseItem>>, response: Response<List<ListFollowResponseItem>>) {
                    if (response.isSuccessful) {
                        val listFollowers  = response.body()
                        if (listFollowers != null) {
                            coroutineScope.launch {
                                val userDetails = listFollowers.map { follow ->
                                    async(Dispatchers.IO) { getDetailFollow(follow.login) }
                                }.awaitAll()

                                _listDetailFollowers.value = userDetails.filterNotNull()
                                _isLoadingFollowers.value = false
                            }
                        }
                    } else {
                        _isLoadingFollowers.value = false
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ListFollowResponseItem>>, t: Throwable) {
                    _isLoadingFollowers.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }


            }
        )

    }

    private fun getListFollowings(username: String) {
        _isLoadingFollowing.value = true
        val client =  ApiConfig.getApiService().getUserFollowings(username)

        client.enqueue(
            object : Callback<List<ListFollowResponseItem>> {
                override fun onResponse(
                    call: Call<List<ListFollowResponseItem>>,
                    response: Response<List<ListFollowResponseItem>>, ) {
                    if (response.isSuccessful) {
                        val listFollowings = response.body()
                        if (listFollowings != null) {
                            coroutineScope.launch {
                                val userDetails = listFollowings.map { follow ->
                                    async(Dispatchers.IO) { getDetailFollow(follow.login) }
                                }.awaitAll()

                                _listDetailFollowings  .value = userDetails.filterNotNull()
                                _isLoadingFollowing.value = false
                            }
                        }

                    } else {
                        _isLoadingFollowing.value = false
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ListFollowResponseItem>>, t: Throwable) {
                    _isLoadingFollowing.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )
    }

    private fun getDetailFollow(username: String): DetailUserResponse? {

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

    fun setUser(userData: DetailUserResponse) {
        _user.value = userData
    }
}
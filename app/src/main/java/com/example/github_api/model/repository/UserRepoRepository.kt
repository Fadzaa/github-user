package com.example.github_api.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.remote.response.RepositoryUserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepoRepository(private val apiService: ApiService) {

    fun getPublicRepo(username: String) : LiveData<List<RepositoryUserResponseItem>> {
        val client = apiService.getUserRepository(username)
        val data = MutableLiveData<List<RepositoryUserResponseItem>>()

        client.enqueue(
            object : Callback<List<RepositoryUserResponseItem>> {
                override fun onResponse(call: Call<List<RepositoryUserResponseItem>>, response: Response<List<RepositoryUserResponseItem>>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<RepositoryUserResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return data
    }

    fun getStarredRepo(username: String) : LiveData<List<RepositoryUserResponseItem>> {
        val client = apiService.getUserStarredRepository(username)
        val data = MutableLiveData<List<RepositoryUserResponseItem>>()

        client.enqueue(
            object : Callback<List<RepositoryUserResponseItem>> {
                override fun onResponse(call: Call<List<RepositoryUserResponseItem>>, response: Response<List<RepositoryUserResponseItem>>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<RepositoryUserResponseItem>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return data
    }

    companion object {
        private const val TAG = "UserRepoRepository"
    }
}
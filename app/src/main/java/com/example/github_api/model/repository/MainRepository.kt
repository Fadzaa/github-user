package com.example.github_api.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.remote.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val apiService: ApiService) {
    fun getMyUserDetail(): LiveData<DetailUserResponse> {
        val user = MutableLiveData<DetailUserResponse>()
        apiService.getMyUserDetail().enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    user.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return user
    }

    companion object {
        private const val TAG = "MainRepository"
    }
}
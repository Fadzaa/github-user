package com.example.github_api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.data.response.ListFollowResponse
import com.example.github_api.data.response.ListFollowResponseItem
import com.example.github_api.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _listFollowers = MutableLiveData<ListFollowResponseItem>()
    val listFollowers: LiveData<ListFollowResponseItem> = _listFollowers

    private val _listFollowings = MutableLiveData<ListFollowResponseItem>()
    val listFollowings: LiveData<ListFollowResponseItem> = _listFollowings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailViewModel"
    }

    init {

    }

    private fun getListUserFollowers() {

    }


}

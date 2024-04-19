package com.example.github_api.model.repository

import com.example.github_api.model.remote.ApiService
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.model.remote.response.ListFollowResponseItem

//class DetailRepository (private val apiService: ApiService) {
//    private fun getListFollowers(username: String): List<DetailUserResponse> {
//        val listFollowers = apiService.getUserFollowers(username).execute().body()
//        return listFollowers.map { follow ->
//            getDetailFollow(follow.login)
//        }.filterNotNull()
//    }
//
//    private fun getListFollowings(username: String): List<ListFollowResponseItem> {
//        // Fetch data from the API and return it
//    }
//
//    private fun getDetailFollow(username: String): DetailUserResponse? {
//        // Fetch data from the API and return it
//    }
//}
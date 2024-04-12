package com.example.github_api.data.retrofit

import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.data.response.ListFollowResponseItem
import com.example.github_api.data.response.RepositoryUserResponseItem
import com.example.github_api.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUsers (
        @Query("q") query: String
    ) : Call<SearchResponse>

    @GET("users/{username}")
    fun getUserDetail (
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("user")
    fun getMyUserDetail () : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers (
        @Path("username") username: String
    ) : Call<List<ListFollowResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowings (
        @Path("username") username: String
    ) : Call<List<ListFollowResponseItem>>

    @GET("/users/{username}/repos")
    fun getUserRepository (
        @Path("username") username: String
    ) : Call<List<RepositoryUserResponseItem>>

    @GET("/users/{username}/starred")
    fun getUserStarredRepository (
        @Path("username") username: String
    ) : Call<List<RepositoryUserResponseItem>>



}
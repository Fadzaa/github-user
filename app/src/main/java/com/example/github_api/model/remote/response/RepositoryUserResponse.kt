package com.example.github_api.model.remote.response

import com.google.gson.annotations.SerializedName

data class RepositoryUserResponseItem(

    @field:SerializedName("stargazers_count")
	val stargazersCount: Int,

    @field:SerializedName("language")
	val language: String,

	@field:SerializedName("id")
	val id: Int,

    @field:SerializedName("forks")
	val forks: Int,

    @field:SerializedName("full_name")
	val fullName: String,

    @field:SerializedName("name")
	val name: String,

    @field:SerializedName("description")
	val description: String,

    @field:SerializedName("forks_count")
	val forksCount: Int
)
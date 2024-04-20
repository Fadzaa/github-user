package com.example.github_api.model.remote.response

import com.google.gson.annotations.SerializedName

data class ListFollowResponseItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

)

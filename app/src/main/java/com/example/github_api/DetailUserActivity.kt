package com.example.github_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    companion object{
        const val EXTRA_USER =  "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user  = intent.getSerializableExtra(EXTRA_USER) as DetailUserResponse

        setUser(user)


    }

    private fun setUser(user: DetailUserResponse) {
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.civProfileUser)

        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
        binding.tvCompany.text = user.company
        binding.tvLocation.text = user.location
        binding.tvFollowers.text = getString(R.string.followers, user.followers)
        binding.tvFollowings.text = getString(R.string.followings, user.following)
        binding.tvPublicRepo.text = getString(R.string.public_repo, user.publicRepos)


    }
}
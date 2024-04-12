package com.example.github_api.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.github_api.R
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.databinding.ActivityDetailUserBinding
import com.example.github_api.ui.adapter.SectionsPagerAdapter
import com.example.github_api.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel: DetailViewModel by viewModels()

    companion object{
        const val EXTRA_USER =  "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user  = intent.getSerializableExtra(EXTRA_USER) as DetailUserResponse

        detailViewModel.setUser(user)

        detailViewModel.user.observe(this) {
            setUser(it)
            setViewPager(it)
        }

        with(binding) {
            ivArrowBack.setOnClickListener {
                finish()
            }
        }

    }

    private fun setUser(user: DetailUserResponse) {
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.civProfileUser)

        binding.tvName.text = user.name
        binding.tvUsername.text = user.login
        binding.tvCompany.text = user.company
        binding.tvLocation.text = user.location
        binding.tvBio.text = user.bio
        binding.tvFollowers.text = getString(R.string.followers, user.followers)
        binding.tvFollowings.text = getString(R.string.followings, user.following)
        binding.tvPublicRepo.text = getString(R.string.public_repo, user.publicRepos)
    }

    private fun setViewPager(user: DetailUserResponse) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        val followers = resources.getString(R.string.followers, user.followers)
        val followings = resources.getString(R.string.followings, user.following)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> followers
                else -> followings
            }
        }.attach()
    }
}
package com.example.github_api.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.github_api.R
import com.example.github_api.databinding.ActivityRepositoryBinding
import com.example.github_api.ui.adapter.RepoPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RepositoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepositoryBinding
    private var username: String = "Fadzaa"


    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        username = intent.getStringExtra(EXTRA_USERNAME).toString()

            setViewPager()

            with(binding) {
                ivArrowBack.setOnClickListener {
                finish()
            }

            tvTitle.text = getString(R.string.repository_user, username)
        }

    }

    private fun setViewPager() {
        val repoPagerAdapter = RepoPagerAdapter(this)
        binding.viewPager.adapter = repoPagerAdapter

        val publicRepo = "Public Repository"
        val starredRepo = "Starred Repository"

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> publicRepo
                else -> starredRepo
            }
        }.attach()
    }
}
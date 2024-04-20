package com.example.github_api.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.github_api.R
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.databinding.ActivityDetailUserBinding
import com.example.github_api.model.local.User
import com.example.github_api.view.adapter.FollowPagerAdapter
import com.example.github_api.viewmodel.DetailViewModel
import com.example.github_api.viewmodel.FavouriteViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var username: String

    private val detailViewModel: DetailViewModel by viewModel { parametersOf(username) }
    private val favouriteViewModel: FavouriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getUsername  = intent.getSerializableExtra(EXTRA_USER) as String?

        if (getUsername != null) {
            username = getUsername
        }


        detailViewModel.user.observe(this){ user ->
            setUser(user)
            setViewPager(user)
            setInitialFavouriteState(user.id)

            with(binding) {
                progressBarHeading?.visibility = View.GONE
                ivFavourite.setOnClickListener {
                    if (ivFavourite.tag == "unfavourite") {
                        setFavouriteUser(user, true)
                        ivFavourite.setImageResource(R.drawable.ic_heart_filled)
                        ivFavourite.tag = "favourite"
                    } else {
                        setFavouriteUser(user, false)
                        ivFavourite.setImageResource(R.drawable.ic_heart)
                        ivFavourite.tag = "unfavourite"
                    }
                }
            }
        }

        with(binding) {
            ivArrowBack.setOnClickListener {
                finish()
            }

            tvPublicRepo.setOnClickListener {
                navigateToRepository()
            }

            ivArrowGreen.setOnClickListener {
                navigateToRepository()
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
        val followPagerAdapter = FollowPagerAdapter(this)
        binding.viewPager.adapter = followPagerAdapter

        val followers = resources.getString(R.string.followers, user.followers)
        val followings = resources.getString(R.string.followings, user.following)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> followers
                else -> followings
            }
        }.attach()
    }

    private fun navigateToRepository() {
        val intent = Intent(this, RepositoryActivity::class.java)
        intent.putExtra(RepositoryActivity.EXTRA_USERNAME, username)
        startActivity(intent)
    }

    private fun setFavouriteUser(user: DetailUserResponse, isFavourite: Boolean) {
        val userFavourite = User(
            user.id,
            user.name,
            user.avatarUrl,
            user.login,
            user.bio,
            user.location,
            user.company,
            user.followers,
            user.following,
            user.publicRepos
        )

        if (isFavourite) {
            favouriteViewModel.insert(userFavourite)
        } else {
            favouriteViewModel.delete(userFavourite)
        }
    }

    private fun setInitialFavouriteState(id: Int) {
        favouriteViewModel.getUserById(id).observe(this) {
            if (it != null) {
                binding.ivFavourite.setImageResource(R.drawable.ic_heart_filled)
                binding.ivFavourite.tag = "favourite"
            } else {
                binding.ivFavourite.setImageResource(R.drawable.ic_heart)
                binding.ivFavourite.tag = "unfavourite"
            }
        }
    }

    companion object{
        const val EXTRA_USER =  "extra_user"
    }
}
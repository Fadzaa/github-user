package com.example.github_api.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.github_api.R
import com.example.github_api.model.preferences.SettingPreferences
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.model.preferences.dataStore
import com.example.github_api.databinding.ActivityMainBinding
import com.example.github_api.view.adapter.ListUserAdapter
import com.example.github_api.viewmodel.MainViewModel
import com.example.github_api.viewmodel.SearchViewModel
import com.example.github_api.viewmodel.ThemeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var pref: SettingPreferences


    private val mainViewModel : MainViewModel by viewModel()
    private val searchViewModel : SearchViewModel by viewModel()
    private val themeViewModel: ThemeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pref = SettingPreferences.getInstance(application.dataStore)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.user.observe(this) {
            setCurrentUser(it)
        }

        mainViewModel.isLoading.observe(this) {
            setHeadingLoading(it)
        }

        searchViewModel.listDetailUsers.observe(this) {
            setRvUser(it)
        }

        searchViewModel.isLoading.observe(this) {
            setLoading(it)
        }

        searchViewModel.isDataEmpty.observe(this) {
            if (it) {
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.tvNoData.visibility = View.GONE
            }
        }

        searchUser()

        navigateTo()

    }

    private fun setCurrentUser(detailUserResponse: DetailUserResponse) {
        val followers = detailUserResponse.followers
        val following = detailUserResponse.following

        binding.tvUserFullname.text = detailUserResponse.name
        binding.tvFollowers.text = getString(R.string.followers, followers)
        binding.tvFollowings.text = getString(R.string.followings, following)
        Glide.with(this)
            .load(detailUserResponse.avatarUrl)
            .into(binding.civCurrentUser)
    }

    private fun setRvUser(listUser: List<DetailUserResponse>) {
        with(binding) {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            listUserAdapter = ListUserAdapter(listUser)
            rvUsers.adapter = listUserAdapter
        }
    }

    private fun setHeadingLoading(isLoading: Boolean) {
        with(binding) {
            progressBarHeading.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvGreeting.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvUserFullname.visibility = if (isLoading) View.GONE else View.VISIBLE
            civCurrentUser.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowings.visibility = if (isLoading) View.GONE else View.VISIBLE
            ivArrow.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun searchUser() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        searchBar.text = searchView.text
                        searchView.hide()
                        searchViewModel.searchUsers(searchView.text.toString())
                        true
                    } else {
                        false
                    }
                }
        }
    }

    private fun navigateTo() {
        with(binding) {
            ivArrow.setOnClickListener {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                val username: String? = mainViewModel.user.value?.login
                intent.putExtra(DetailUserActivity.EXTRA_USER, username)
                startActivity(intent)
            }

            ivFavourite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
            }

            ivSettings.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }


    }
}
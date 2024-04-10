package com.example.github_api.ui.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.github_api.DetailUserActivity
import com.example.github_api.R
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.databinding.ActivityMainBinding
import com.example.github_api.ui.adapter.ListUserAdapter
import com.example.github_api.viewmodel.MainViewModel
import com.example.github_api.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var listUserAdapter: ListUserAdapter

    private val mainViewModel by viewModels<MainViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        mainViewModel.user.observe(this) {
            setCurrentUser(it)
        }

        searchViewModel.listDetailUsers.observe(this) {
            setRvUser(it)
        }

        mainViewModel.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        binding.ivArrow.setOnClickListener {
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, mainViewModel.user.value)
            startActivity(intent)
        }
    }

    private fun setCurrentUser(detailUserResponse: DetailUserResponse) {
        val followers = detailUserResponse.followers
        val following = detailUserResponse.following
        val repository = detailUserResponse.publicRepos

        binding.tvUserFullname.text = detailUserResponse.name
        binding.tvFollowers.text = getString(R.string.followers, followers)
        binding.tvFollowings.text = getString(R.string.followings, following)
        binding.tvPublicRepo.text = getString(R.string.public_repo, repository)
        Glide.with(this)
            .load(detailUserResponse.avatarUrl)
            .into(binding.civCurrentUser)
    }

    private fun setRvUser(listUser: List<DetailUserResponse>) {
        listUserAdapter = ListUserAdapter(listUser)
        binding.rvUsers.adapter = listUserAdapter
    }


}
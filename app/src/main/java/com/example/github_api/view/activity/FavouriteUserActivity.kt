package com.example.github_api.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api.databinding.ActivityFavouriteUserBinding
import com.example.github_api.view.adapter.ListFavouriteUserAdapter
import com.example.github_api.viewmodel.FavouriteViewModel
import com.example.github_api.viewmodel.FavouriteViewModelFactory

class FavouriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteUserBinding

    private lateinit var favouriteViewModel: FavouriteViewModel

    private lateinit var adapter: ListFavouriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favouriteViewModel = obtainViewModel(this@FavouriteUserActivity)

        binding.rvFavouriteUser.setHasFixedSize(true)
        binding.rvFavouriteUser.layoutManager = LinearLayoutManager(this)

        favouriteViewModel.getAllUser().observe(this) {
            adapter = ListFavouriteUserAdapter(it)
            binding.rvFavouriteUser.adapter = adapter
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavouriteViewModel {
        val factory = FavouriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavouriteViewModel::class.java]
    }
}
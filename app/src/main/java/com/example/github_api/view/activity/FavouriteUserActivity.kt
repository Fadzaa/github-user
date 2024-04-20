package com.example.github_api.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api.databinding.ActivityFavouriteUserBinding
import com.example.github_api.view.adapter.ListFavouriteUserAdapter
import com.example.github_api.viewmodel.FavouriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteUserBinding

    private val favouriteViewModel: FavouriteViewModel by viewModel()

    private lateinit var adapter: ListFavouriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.rvFavouriteUser.setHasFixedSize(true)
        binding.rvFavouriteUser.layoutManager = LinearLayoutManager(this)

        favouriteViewModel.getAllUser().observe(this) {
            adapter = ListFavouriteUserAdapter(it)
            binding.rvFavouriteUser.adapter = adapter
        }

        binding.ivArrowBack.setOnClickListener {
            finish()
        }

    }
}
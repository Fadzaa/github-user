package com.example.github_api.viewmodel_injection

import androidx.lifecycle.ViewModelProvider
import com.example.github_api.model.remote.ApiService
import com.example.github_api.viewmodel.SearchViewModel

class SearchViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
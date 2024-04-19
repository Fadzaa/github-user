package com.example.github_api.viewmodel_injection

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.example.github_api.viewmodel.FavouriteViewModel

class FavouriteViewModelFactory (private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
        companion object {
            @Volatile
            private var INSTANCE: FavouriteViewModelFactory? = null
            @JvmStatic
            fun getInstance(application: Application): FavouriteViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(FavouriteViewModelFactory::class.java) {
                        INSTANCE = FavouriteViewModelFactory(application)
                    }
                }
                return INSTANCE as FavouriteViewModelFactory
            }
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}
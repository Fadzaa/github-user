package com.example.github_api.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.github_api.databinding.ActivitySettingsBinding
import com.example.github_api.model.preferences.SettingPreferences
import com.example.github_api.model.preferences.dataStore
import com.example.github_api.viewmodel.ThemeViewModel
import com.example.github_api.viewmodel_injection.ThemeViewModelFactory

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private lateinit var pref: SettingPreferences
    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(pref)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SettingPreferences.getInstance(application.dataStore)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchButton.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchButton.isChecked = false
            }
        }

        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
           themeViewModel.saveThemeSetting(isChecked )
        }
    }
}
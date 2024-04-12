package com.example.github_api.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github_api.ui.fragment.RepoFragment
import com.example.github_api.ui.fragment.RepoType

class RepoPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = RepoFragment.newInstance(RepoType.PUBLIC)
            }
            1 -> {
                fragment = RepoFragment.newInstance(RepoType.STARRED)
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}

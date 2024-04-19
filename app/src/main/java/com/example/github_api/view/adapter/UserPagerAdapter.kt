package com.example.github_api.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github_api.view.fragment.FollowFragment
import com.example.github_api.view.fragment.FollowType

class FollowPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = FollowFragment.newInstance(FollowType.FOLLOWERS)
            }
            1 -> {
                fragment = FollowFragment.newInstance(FollowType.FOLLOWING)
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}
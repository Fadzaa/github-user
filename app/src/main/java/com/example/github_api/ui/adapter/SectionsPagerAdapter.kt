package com.example.github_api.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.ui.fragment.FollowFragment

class SectionsPagerAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        when(position){
            0 -> {
                fragment = FollowFragment()
//                bundle.putSerializable(FollowFragment.ARG_USER_LIST, followers as ArrayList)
            }
//            1 -> {
//                fragment = FollowFragment()
//                bundle.putSerializable(FollowFragment.ARG_USER_LIST, following as ArrayList)
//            }
        }
        fragment?.arguments = bundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 1
    }

}
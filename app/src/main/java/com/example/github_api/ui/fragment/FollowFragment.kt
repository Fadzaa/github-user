package com.example.github_api.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api.R
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.databinding.FragmentFollowBinding
import com.example.github_api.ui.adapter.ListUserAdapter
import com.example.github_api.viewmodel.DetailViewModel


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null

    private lateinit var userList: List<DetailUserResponse>
    private val detailViewModel by activityViewModels<DetailViewModel>()

//    companion object {
//        const val ARG_USER_LIST = "user_list"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            userList = it.getSerializable(ARG_USER_LIST) as List<DetailUserResponse>
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
          return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFollowBinding.bind(view)
        _binding = binding

        binding.rvFollow.setHasFixedSize(true)
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())

        detailViewModel.listDetailFollowings.observe(viewLifecycleOwner){
            val listUserAdapter = ListUserAdapter(it)
            binding.rvFollow.adapter = listUserAdapter
        }

    }

}
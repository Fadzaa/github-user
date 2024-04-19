package com.example.github_api.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api.R
import com.example.github_api.databinding.FragmentFollowBinding
import com.example.github_api.view.adapter.ListUserAdapter
import com.example.github_api.viewmodel.DetailViewModel


class FollowFragment : Fragment() {
    private lateinit var followType: FollowType

    private var _binding: FragmentFollowBinding? = null

    private val detailViewModel by activityViewModels<DetailViewModel>()

    companion object {
        private const val ARG_FOLLOW_TYPE = "arg_follow_type"

        @JvmStatic
        fun newInstance(followType: FollowType) = FollowFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_FOLLOW_TYPE, followType)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            followType = it.getSerializable(ARG_FOLLOW_TYPE) as FollowType
        }
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

        when (followType) {
            FollowType.FOLLOWERS -> {
                detailViewModel.listDetailFollowers.observe(viewLifecycleOwner) {
                    val adapter = ListUserAdapter(it)
                    binding.rvFollow.adapter = adapter
                }

                detailViewModel.isLoadingFollowers.observe(viewLifecycleOwner) {
                    binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
            FollowType.FOLLOWING -> {
                detailViewModel.listDetailFollowings.observe(viewLifecycleOwner) {
                    val adapter = ListUserAdapter(it)
                    binding.rvFollow.adapter = adapter
                }

                detailViewModel.isLoadingFollowing.observe(viewLifecycleOwner) {
                    binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
        }





    }

}

enum class FollowType {
    FOLLOWERS,
    FOLLOWING
}
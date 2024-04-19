package com.example.github_api.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_api.R
import com.example.github_api.databinding.FragmentRepoBinding
import com.example.github_api.model.remote.ApiConfig
import com.example.github_api.view.adapter.ListRepoAdapter
import com.example.github_api.viewmodel.RepositoryViewModel
import com.example.github_api.viewmodel_injection.RepositoryViewModelFactory


class RepoFragment : Fragment() {
    private lateinit var repoType: RepoType
    private lateinit var  username: String
    private var _binding: FragmentRepoBinding? = null

    private val repoViewModel by activityViewModels<RepositoryViewModel>{
        RepositoryViewModelFactory(username, ApiConfig.getApiService())
    }

    companion object {
        private const val ARG_REPO_TYPE = "arg_repo_type"

        @JvmStatic
        fun newInstance(repoType: RepoType, username: String) = RepoFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_REPO_TYPE, repoType)
                putString("username", username)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repoType = it.getSerializable(ARG_REPO_TYPE) as RepoType
            username = it.getString("username").toString()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepoBinding.bind(view)
        _binding = binding

        binding.rvRepo.setHasFixedSize(true)
        binding.rvRepo.layoutManager = LinearLayoutManager(requireContext())

        when (repoType) {
            RepoType.PUBLIC -> {
                repoViewModel.listRepository.observe(viewLifecycleOwner) {
                    val adapter = ListRepoAdapter(it)
                    binding.rvRepo.adapter = adapter
                }
            }
            RepoType.STARRED -> {
                repoViewModel.listStarredRepository.observe(viewLifecycleOwner) {
                    val adapter = ListRepoAdapter(it)
                    binding.rvRepo.adapter = adapter
                }
            }
        }

        repoViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

    }
}

enum class RepoType {
    PUBLIC,
    STARRED
}
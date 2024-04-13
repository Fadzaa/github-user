package com.example.github_api.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_api.R
import com.example.github_api.data.response.RepositoryUserResponseItem
import com.example.github_api.databinding.ItemRepoVerticalBinding


class ListRepoAdapter(private val listRepo: List<RepositoryUserResponseItem>) : RecyclerView.Adapter<ListRepoAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRepoVerticalBinding = ItemRepoVerticalBinding.bind(itemView)

        fun bind(repo: RepositoryUserResponseItem) {
            with(binding) {
                tvName.text = repo.name
                tvDescription.text = repo.description
                tvFork.text = repo.forksCount.toString()
                tvStar.text = repo.stargazersCount.toString()
                tvLanguage.text = repo.language

                if (repo.description != null) {
                    tvDescription.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo_vertical, parent, false)
        return  ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listRepo[position])
    }

    override fun getItemCount(): Int {
        return listRepo.size
    }


}
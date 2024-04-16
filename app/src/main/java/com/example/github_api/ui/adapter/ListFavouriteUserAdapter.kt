package com.example.github_api.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_api.R
import com.example.github_api.database.User
import com.example.github_api.databinding.ItemUserVerticalBinding

class ListFavouriteUserAdapter(private val listUser: List<User>) : RecyclerView.Adapter<ListFavouriteUserAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUserVerticalBinding.bind(itemView)

        fun bind(user: User) {
            with(binding) {
                tvUsername.text = user.username
                tvName.text = user.name
                tvLocation.text = user.location
                tvCompany.text = user.company
                tvBio.text = user.bio
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_vertical, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}
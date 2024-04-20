package com.example.github_api.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_api.view.activity.DetailUserActivity
import com.example.github_api.R
import com.example.github_api.model.remote.response.DetailUserResponse
import com.example.github_api.databinding.ItemUserVerticalBinding

class ListUserAdapter(private var listUser: List<DetailUserResponse>) : RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {

    class ListUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserVerticalBinding.bind(itemView)

        fun bind(user: DetailUserResponse) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(civProfileUser)

                tvUsername.text = itemView.context.getString(
                    R.string.username,
                    user.login
                )
                tvBio.text = user.bio
                tvCompany.text =  user.company
                tvLocation.text = user.location
                tvName.text = user.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_vertical, parent, false)
        return ListUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.bind(listUser[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, listUser[position].login)
            holder.itemView.context.startActivity(intent)
        }
    }

}

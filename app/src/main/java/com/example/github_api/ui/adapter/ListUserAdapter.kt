package com.example.github_api.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_api.R
import com.example.github_api.data.response.DetailUserResponse
import com.example.github_api.data.response.ItemsItem
import com.example.github_api.databinding.ItemUserVerticalBinding

class ListUserAdapter(
    private var listUser: List<DetailUserResponse>
) : RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {


//    fun updateData(newListUser: List<DetailUserResponse>) {
//        listUser = newListUser
//        notifyDataSetChanged()
//    }


    class ListUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserVerticalBinding.bind(itemView)

        fun bind(user: DetailUserResponse) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(civProfileUser)

                tvUsername.text = user.login
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
    }

}

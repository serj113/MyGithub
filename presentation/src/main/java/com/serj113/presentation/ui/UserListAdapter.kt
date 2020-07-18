package com.serj113.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.serj113.domain.entity.User
import com.serj113.presentation.databinding.UserListItemBinding

class UserListAdapter : PagedListAdapter<User, UserListAdapter.UserItemViewHolder>(UserItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        return UserItemViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class UserItemViewHolder(
        private val binding: UserListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.titleTextView.text = user.login
            Glide.with(binding.root)
                .load(user.avatar_url)
                .into(binding.bookImageView)
        }
    }
}
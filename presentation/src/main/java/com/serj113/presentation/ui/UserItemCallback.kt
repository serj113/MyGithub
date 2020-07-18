package com.serj113.presentation.ui

import androidx.recyclerview.widget.DiffUtil
import com.serj113.domain.entity.User

object UserItemCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}
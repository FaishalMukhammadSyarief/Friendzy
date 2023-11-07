package com.zhalz.friendzy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zhalz.friendzy.R
import com.zhalz.friendzy.data.Friend
import com.zhalz.friendzy.databinding.ItemFriendsBinding

class FriendAdapter(var items: List<Friend>): RecyclerView.Adapter<FriendAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(var binding: ItemFriendsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_friends, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.tvName.text = items[position].name
        holder.binding.tvBirth.text = items[position].birth
    }
}
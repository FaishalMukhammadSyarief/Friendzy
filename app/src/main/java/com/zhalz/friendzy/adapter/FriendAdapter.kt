package com.zhalz.friendzy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zhalz.friendzy.R
import com.zhalz.friendzy.data.database.FriendEntity
import com.zhalz.friendzy.databinding.ItemFriendsBinding

class FriendAdapter(private var items: List<FriendEntity>, val onItemClick : (FriendEntity) -> Unit): RecyclerView.Adapter<FriendAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(var binding: ItemFriendsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_friends, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.friendData = items[position]
        holder.itemView.setOnClickListener { onItemClick(items[position]) }
        holder.binding.executePendingBindings()
    }
}
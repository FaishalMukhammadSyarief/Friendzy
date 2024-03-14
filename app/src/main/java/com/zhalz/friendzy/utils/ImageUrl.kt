package com.zhalz.friendzy.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zhalz.friendzy.R

object ImageUrl {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadUrlString(imageUrl: String?) {
        Glide.with(context)
            .load(imageUrl)
            .error(R.drawable.deer)
            .into(this)
    }

}
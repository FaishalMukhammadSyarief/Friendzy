package com.zhalz.friendzy.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object PhotoHelper {

    @JvmStatic
    @BindingAdapter("imageSrc")
    fun ImageView.setImageSrc(stringPhoto: String?) {
        stringPhoto?.let {
            val bitmapPhoto = BitmapHelper().stringToBitmap(this.context, it)
            setImageBitmap(bitmapPhoto)
        }
    }

}
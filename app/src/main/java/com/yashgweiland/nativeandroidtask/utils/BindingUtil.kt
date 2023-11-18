package com.yashgweiland.nativeandroidtask.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.yashgweiland.nativeandroidtask.R

object BindingUtil {

    @BindingAdapter("circularImageUrl", "errorDrawable", "isDrawable")
    @JvmStatic
    fun setCircularImage(imageView: ImageView, url: String?, errorDrawable: Drawable?, isDrawable: Boolean) {
        var errorDrawable = errorDrawable
        if (url.isNullOrEmpty()) {
            errorDrawable =
                ContextCompat.getDrawable(
                    imageView.context,
                    R.drawable.icon__bitcoin
                )
        }
        val context = imageView.context
        val res = context.resources
        if (errorDrawable == null) {
            errorDrawable =
                ContextCompat.getDrawable(
                    imageView.context,
                    R.drawable.icon__bitcoin
                )
        }
        if (isDrawable)
            Glide.with(context).load(getImage(url!!, context)).error(errorDrawable).into(imageView)
        else
            Glide.with(context).load(url).skipMemoryCache(false).error(errorDrawable).into(imageView)
    }
    private fun getImage(imageName: String, context: Context): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}
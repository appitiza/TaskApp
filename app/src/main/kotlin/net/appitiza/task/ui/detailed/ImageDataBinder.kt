package net.appitiza.task.ui.detailed

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    val context = imageView.context
    Glide.with(context).load(url).into(imageView)
}
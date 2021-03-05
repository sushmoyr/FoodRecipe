package com.sushmoyr.foodrecipe.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.sushmoyr.foodrecipe.R

class RecipesRowBinding {
    companion object {

        @BindingAdapter("android:loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url:String)
        {
            imageView.load(url){
                crossfade(600)
            }
        }

        @BindingAdapter("android:setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("android:setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("android:applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean)
        {
            if(vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }
    }
}
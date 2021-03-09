package com.sushmoyr.foodrecipe.adapters.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.models.Result
import com.sushmoyr.foodrecipe.ui.fragments.RecipesFragmentDirections
import org.jsoup.Jsoup

class RecipesRowBinding {
    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result){
            recipeRowLayout.setOnClickListener{
                Log.d("onRecipeClickListener","Clicked")
                try {
                    val action =
                        RecipesFragmentDirections.actionRecipiesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                }catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("android:loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url:String)
        {
            imageView.load(url){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
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

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, value:String?){
            if(value != null){
                val desc = Jsoup.parse(value).text()
                textView.text = desc
            }
        }
    }
}
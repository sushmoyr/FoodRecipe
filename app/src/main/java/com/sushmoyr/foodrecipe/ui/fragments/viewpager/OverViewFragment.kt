package com.sushmoyr.foodrecipe.ui.fragments.viewpager

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import coil.load
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.models.Result
import kotlinx.android.synthetic.main.fragment_over_view.view.*
import org.jsoup.Jsoup

class OverViewFragment : Fragment() {

    private lateinit var mView: View
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_over_view, container, false)

        val args = arguments
        val bundle: Result? = args?.getParcelable("recipeBundle")

        mView.mainImageView.load(bundle?.image)
        mView.titleTextView.text = bundle?.title
        mView.likesTextView.text = bundle?.aggregateLikes.toString()
        mView.timeTextView.text = bundle?.readyInMinutes.toString()
        bundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            mView.summaryTextView.text = summary
            mView.summaryTextView.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        setChips(bundle)

        return mView
    }

    private fun setChips(bundle: Result?)
    {
        if (bundle?.vegetarian == true) {
            mView.vegetarianImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            mView.vegetarianTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        if (bundle?.vegan == true) {
            mView.veganImageView?.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            mView.vegan_textView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        if (bundle?.glutenFree == true) {
            mView.glutenFreeImageView?.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            mView.glutenFreeTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        if (bundle?.dairyFree == true) {
            mView.dairyFreeImageView?.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            mView.dairyFreeTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        if (bundle?.veryHealthy == true) {
            mView.healthyImageView?.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            mView.healthyTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        if (bundle?.cheap == true) {
            mView.cheapImageView?.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            mView.cheapTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
    }

}
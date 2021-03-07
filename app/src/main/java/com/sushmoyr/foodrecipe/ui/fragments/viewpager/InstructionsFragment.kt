package com.sushmoyr.foodrecipe.ui.fragments.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.models.Result
import kotlinx.android.synthetic.main.fragment_instructions.view.*

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val bundle: Result? = args?.getParcelable("recipeBundle")

        view.instruction_webView.webViewClient = object : WebViewClient() {

        }
        val websiteUrl: String = bundle!!.sourceUrl
        view.instruction_webView.loadUrl(websiteUrl)

        return view
    }

}
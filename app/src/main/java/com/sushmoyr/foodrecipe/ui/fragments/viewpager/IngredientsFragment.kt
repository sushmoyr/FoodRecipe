package com.sushmoyr.foodrecipe.ui.fragments.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.adapters.IngredientsAdapter
import com.sushmoyr.foodrecipe.models.Result
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {

    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val bundle: Result? = args?.getParcelable("recipeBundle")

        setUpRecyclerView(view)
        bundle?.extendedIngredients?.let { adapter.setData(it) }

        return view
    }

    private fun setUpRecyclerView(view: View){
        view.ingredients_recycler_view.adapter = adapter
        view.ingredients_recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }


}
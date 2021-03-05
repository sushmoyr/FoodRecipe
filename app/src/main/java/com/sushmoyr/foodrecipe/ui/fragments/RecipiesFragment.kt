package com.sushmoyr.foodrecipe.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushmoyr.foodrecipe.MainViewModel
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.RecipesViewModel
import com.sushmoyr.foodrecipe.adapters.RecipesAdapter
import com.sushmoyr.foodrecipe.util.Constants.Companion.API_KEY
import com.sushmoyr.foodrecipe.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipies.view.*

@AndroidEntryPoint
class RecipiesFragment : Fragment() {

    private val adapter by lazy { RecipesAdapter() }
    private lateinit var mView: View
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipies, container, false)


        setUpRecyclerView()
        requestApiData()

        return mView
    }

    private fun setUpRecyclerView(){
        mView.shimmer_recycler_view.adapter = adapter
        mView.shimmer_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun requestApiData(){
        mainViewModel.getRecipes(recipeViewModel.applyQueries())

        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }


    private fun showShimmerEffect(){
        mView.shimmer_recycler_view.showShimmer()
    }

    private fun hideShimmerEffect()
    {
        mView.shimmer_recycler_view.hideShimmer()
    }

}
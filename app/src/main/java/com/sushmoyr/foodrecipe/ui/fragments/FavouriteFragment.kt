package com.sushmoyr.foodrecipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sushmoyr.foodrecipe.adapters.FavouritesRecipeAdapter
import com.sushmoyr.foodrecipe.databinding.FragmentFavouriteBinding
import com.sushmoyr.foodrecipe.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val adapter: FavouritesRecipeAdapter by lazy {
        FavouritesRecipeAdapter(requireActivity(), mainViewModel)
    }


    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.adapter = adapter

        setUpRecyclerView(binding.favouritesRecyclerView)

        mainViewModel.readFavouritesRecipe.observe(viewLifecycleOwner, { favouritesEntity->
            adapter.setData(favouritesEntity)
        })

        return binding.root
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView){
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapter.clearContextualActionMode()
    }
}
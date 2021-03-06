package com.sushmoyr.foodrecipe.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sushmoyr.foodrecipe.data.DataStoreRepository
import com.sushmoyr.foodrecipe.util.Constants.Companion.API_KEY
import com.sushmoyr.foodrecipe.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.sushmoyr.foodrecipe.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.sushmoyr.foodrecipe.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_API_KEY
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_DIET
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_NUMBER
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_TYPE
import com.sushmoyr.foodrecipe.util.Constants.Companion.QUERY_SEARCH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    var networkStatus: Boolean = false
    var backOnline: Boolean = false

    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        
        Log.d("latest", "Api key = $API_KEY")
        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries = HashMap<String, String>()

        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if(networkStatus){
            if(backOnline){
                Toast.makeText(getApplication(), "We're back online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }


}
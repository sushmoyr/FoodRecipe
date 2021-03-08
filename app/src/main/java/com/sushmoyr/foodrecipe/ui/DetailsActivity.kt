package com.sushmoyr.foodrecipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sushmoyr.foodrecipe.R
import com.sushmoyr.foodrecipe.adapters.PagerAdapter
import com.sushmoyr.foodrecipe.data.database.entities.FavouritesEntity
import com.sushmoyr.foodrecipe.ui.fragments.viewpager.IngredientsFragment
import com.sushmoyr.foodrecipe.ui.fragments.viewpager.InstructionsFragment
import com.sushmoyr.foodrecipe.ui.fragments.viewpager.OverViewFragment
import com.sushmoyr.foodrecipe.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved:Boolean = false
    private var savedRecipeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverViewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        else if(item.itemId == R.id.save_favourite && !recipeSaved){
            saveToFavourites(item)
            Log.d("details","Recipe adding")
        } else if(item.itemId == R.id.save_favourite && recipeSaved){
            Log.d("details","Recipe removing")
            removeFromFavourites(item)
        }


        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouritesEntity = FavouritesEntity(
            0,
            args.result
        )
        mainViewModel.insertFavouriteRecipes(favouritesEntity)
        showSnackBar("Recipe Saved")
        changeMenuItemColor(item, true)
        recipeSaved = true
    }

    private fun removeFromFavourites(item: MenuItem){
        val favouritesEntity = FavouritesEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavouriteRecipes(favouritesEntity)
        recipeSaved = false
        showSnackBar("Recipe Removed")
        changeMenuItemColor(item, false)
    }

    private fun changeMenuItemColor(item: MenuItem, isRecipeSaved: Boolean) {
        if(isRecipeSaved){
            item.setIcon(R.drawable.ic_star_filled)
            item.icon.setTint(ContextCompat.getColor(this, R.color.yellow))
        }
        else{
            item.setIcon(R.drawable.ic_star_outline)
            item.icon.setTint(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_favourite)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavouritesRecipe.observe(this, {favouritesEntity->
            try {

                for (savedRecipe in favouritesEntity){
                    if (savedRecipe.result.id == args.result.id){
                        changeMenuItemColor(menuItem, true)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                        break
                    }
                    else{
                        changeMenuItemColor(menuItem, false)
                    }
                }

            }catch (e: Exception){
                Log.d("details",e.toString())
            }
        })
    }
}
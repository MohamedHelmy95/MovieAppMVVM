package com.example.mymvdb.ui.ui.favorites

import androidx.lifecycle.LiveData
import com.example.mymvdb.database.FavoritesDatabaseDAO
import com.example.mymvdb.database.MovieDetails


class FavoritesViewModelRepository (private val favoritesDatabaseDAO: FavoritesDatabaseDAO){

     fun getAll():LiveData<List<MovieDetails>>{return  favoritesDatabaseDAO.getAll()}


     suspend fun insert(movie: MovieDetails) {
         favoritesDatabaseDAO.insert(movie)
    }
     suspend fun delete(movie: MovieDetails) {
         favoritesDatabaseDAO.delete(movie)
    }



    suspend fun checkIfExists(id:Int):Boolean{
        return favoritesDatabaseDAO.checkIfExists(id) != 0
    }
}
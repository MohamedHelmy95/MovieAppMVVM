package com.example.mymvdb.ui.ui.favorites

import androidx.lifecycle.ViewModel
import com.example.mymvdb.database.MovieDetails

class FavoritesViewModel(val database: FavoritesViewModelRepository) : ViewModel() {
   suspend fun delete(movie: MovieDetails){

       database.delete(movie)
   }
    suspend fun insert(movie: MovieDetails){
        database.insert(movie)
    }
    suspend fun checkIfExists(id:Int):Boolean{
         return  database.checkIfExists(id)
    }

}
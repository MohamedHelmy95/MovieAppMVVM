package com.example.mymvdb.ui.ui.singlemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.utility.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieDetailsRepository: MovieDetailsRepository, movieId : Int) : ViewModel(){
    private val compositeDisposable=CompositeDisposable()
    val movie:LiveData<Movie> by lazy {
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }
    val networkState:LiveData<NetworkState> by lazy{
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
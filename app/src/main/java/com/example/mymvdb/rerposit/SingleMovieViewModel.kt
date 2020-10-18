package com.example.mymvdb.reposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymvdb.movieDetail.MovieDetails
import com.example.mymvdb.utility.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieDetailsRepository: MovieDetailsRepository,movieId : Int) : ViewModel(){
    private val compositeDisposable=CompositeDisposable()
    val movieDetails:LiveData<MovieDetails> by lazy {
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
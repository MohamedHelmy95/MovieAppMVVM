package com.example.mymvdb.ui.ui.singlemovie

import androidx.lifecycle.LiveData
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.movieDetail.MovieDetails
import com.example.mymvdb.utility.MovieDetailNetworkDataSource
import com.example.mymvdb.utility.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService:TMDBInterface) {
    lateinit var movieDetailNetworkDataSource: MovieDetailNetworkDataSource
    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable,movieId : Int) : LiveData<MovieDetails> {
        movieDetailNetworkDataSource= MovieDetailNetworkDataSource(apiService,compositeDisposable)
        movieDetailNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailNetworkDataSource.movieDetailResponse
    }

fun getMovieDetailsNetworkState():LiveData<NetworkState>{
    return movieDetailNetworkDataSource.networkState
}

}
package com.example.mymvdb.utility

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.movieDetail.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailNetworkDataSource(private val apiService:TMDBInterface,private val compositeDisposable:CompositeDisposable) {
    private val _networkstate=MutableLiveData <NetworkState>()
val networkState : LiveData<NetworkState>
    get()=_networkstate
    private val _movieDetailsResponse= MutableLiveData<MovieDetails>()
    val movieDetailResponse:LiveData<MovieDetails>
    get()=_movieDetailsResponse
    fun fetchMovieDetails(movieId:Int){
        _networkstate.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId).subscribeOn(Schedulers.io()).subscribe(
                    {
                    _movieDetailsResponse.postValue(it)
                        _networkstate.postValue(NetworkState.LOADED)
                },
                    {
                        _networkstate.postValue(NetworkState.ERROR)
                        Log.e("MovieDetailSource",it.message.toString())
                    }
                )
            )
        }catch (e:Exception){
            Log.e("MovieDetailSource",e.message.toString())
        }
    }


}
package com.example.mymvdb.rerposit

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.movieDetail.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory (private val apiService:TMDBInterface,private val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int, Movie>()
{
val movieLiveDataSource=MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
val movieDataSource=MovieDataSource(apiService,compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource;
    }


}
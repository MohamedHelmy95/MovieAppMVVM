package com.example.mymvdb.rerposit.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymvdb.api.POST_PER_PAGE
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.utility.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository (private val apiService:TMDBInterface){
    lateinit var moviePagedList:LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory
    fun fetchLiveMoviePageList ( compositeDisposable: CompositeDisposable ) : LiveData<PagedList<Movie>> {
        movieDataSourceFactory= MovieDataSourceFactory(  apiService ,compositeDisposable)
        val config:PagedList.Config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE).build()
moviePagedList=LivePagedListBuilder(movieDataSourceFactory,config).build()
        return moviePagedList
    }

fun getNetworkState():LiveData<NetworkState>{
    return Transformations.switchMap<MovieDataSource,NetworkState>(
        movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState)

}
}
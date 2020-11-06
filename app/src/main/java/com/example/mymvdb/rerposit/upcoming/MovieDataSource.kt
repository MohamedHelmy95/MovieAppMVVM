package com.example.mymvdb.rerposit.upcoming

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymvdb.api.FIRST_PAGE
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.utility.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService:TMDBInterface,private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, Movie>() {
    private var page= FIRST_PAGE
    val networkState : MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
            networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getUpcomingMovies(page).subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.moviesList, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message.toString() )
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getUpcomingMovies(params.key).subscribeOn(Schedulers.io())
                .subscribe({
                   if (it.total_pages>= params.key){
                       callback.onResult(it.moviesList, params.key+1)
                       networkState.postValue(NetworkState.LOADED)
                   }else{
                       networkState.postValue(NetworkState.ENDOFLIST)
                   }
                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message.toString() )
                    }
                )
        )
    }


}
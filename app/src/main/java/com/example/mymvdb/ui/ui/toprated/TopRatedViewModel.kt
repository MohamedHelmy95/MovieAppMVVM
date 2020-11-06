package com.example.mymvdb.ui.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.rerposit.toprated.MoviePageListRepository
import com.example.mymvdb.utility.NetworkState
import io.reactivex.disposables.CompositeDisposable

class TopRatedViewModel(private  val moviePageListRepository: MoviePageListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        moviePageListRepository.fetchLiveMoviePageList(compositeDisposable)
    }
    val networkState: LiveData<NetworkState> by lazy {
        moviePageListRepository.getNetworkState()
    }
    fun listIsEmpty():Boolean{
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
package com.example.mymvdb.ui.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.utility.NetworkState
import io.reactivex.disposables.CompositeDisposable

 class PopularViewModel(private  val moviePageListRepository: MoviePageListRepository) : ViewModel() {

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

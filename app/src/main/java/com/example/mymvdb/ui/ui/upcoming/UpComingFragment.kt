package com.example.mymvdb.ui.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymvdb.api.TMDBClient
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.databinding.FragmentUpcomingBinding
import com.example.mymvdb.rerposit.upcoming.MoviePageListRepository


import com.example.mymvdb.utility.NetworkState

class UpComingFragment : Fragment() {
    lateinit var pagedListRepository: MoviePageListRepository
    private lateinit var binding: FragmentUpcomingBinding
    private lateinit var upComingViewModel: UpComingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding=  FragmentUpcomingBinding.inflate( inflater,container, false)
        val apiService: TMDBInterface = TMDBClient.getClient()
        pagedListRepository= MoviePageListRepository(apiService)
        upComingViewModel=getViewModel()
        val gridLayoutManager= GridLayoutManager(this.context,2)
        val adapter= UpcomingPagedListAdapter(this.context)
        gridLayoutManager.spanSizeLookup= object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType:Int=adapter.getItemViewType(position)
                if(viewType==adapter.MOVIE_VIEW_TYPE)  return 1
                else return 2
            }
        }
        binding.upcomingMoviesRv.apply {
            layoutManager=gridLayoutManager
            setHasFixedSize(true)
            this.adapter=adapter
        }
        upComingViewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        upComingViewModel.networkState.observe(viewLifecycleOwner, Observer {
            binding.progressBarUpcoming.visibility=if(upComingViewModel.listIsEmpty() && it== NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.textErrorUpcoming.visibility=if (upComingViewModel.listIsEmpty() && it== NetworkState.ERROR) View.VISIBLE else View.GONE
            if(!upComingViewModel.listIsEmpty()){
                adapter.setNetworkState(it)
            }
        })


        return binding.root
      
    }
    private fun getViewModel(): UpComingViewModel {
        return ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHEKED_CAST")
                return  UpComingViewModel(pagedListRepository) as T
            }
        })[UpComingViewModel::class.java]
    }
}
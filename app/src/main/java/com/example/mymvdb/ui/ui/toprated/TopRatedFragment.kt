package com.example.mymvdb.ui.ui.toprated

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
import com.example.mymvdb.databinding.FragmentTopratedBinding
import com.example.mymvdb.rerposit.toprated.MoviePageListRepository
import com.example.mymvdb.utility.NetworkState

class TopRatedFragment : Fragment() {

    lateinit var pagedListRepository: MoviePageListRepository
    private lateinit var binding: FragmentTopratedBinding
    private lateinit var topRatedViewModel: TopRatedViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding=  FragmentTopratedBinding.inflate( inflater,container, false)
        val apiService: TMDBInterface = TMDBClient.getClient()
        pagedListRepository= MoviePageListRepository(apiService)
       topRatedViewModel=getViewModel()
        val gridLayoutManager= GridLayoutManager(this.context,2)
        val adapter= TopRatedPagedListAdapter(this.context)
        gridLayoutManager.spanSizeLookup= object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType:Int=adapter.getItemViewType(position)
                if(viewType==adapter.MOVIE_VIEW_TYPE)  return 1
                else return 2
            }
        }
        binding.topRatedMoviesRv.apply {
            layoutManager=gridLayoutManager
            setHasFixedSize(true)
            this.adapter=adapter
        }
        topRatedViewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        topRatedViewModel.networkState.observe(viewLifecycleOwner, Observer {
            binding.progressBarTop.visibility=if(topRatedViewModel.listIsEmpty() && it== NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.textErrorTop.visibility=if (topRatedViewModel.listIsEmpty() && it== NetworkState.ERROR) View.VISIBLE else View.GONE
            if(!topRatedViewModel.listIsEmpty()){
                adapter.setNetworkState(it)
            }
        })


        return binding.root
    }





    private fun getViewModel():TopRatedViewModel{
        return ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHEKED_CAST")
                return  TopRatedViewModel(pagedListRepository)as T
            }
        })[TopRatedViewModel::class.java]
    }
}
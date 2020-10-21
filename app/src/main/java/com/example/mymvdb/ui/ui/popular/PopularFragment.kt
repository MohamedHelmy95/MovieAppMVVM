package com.example.mymvdb.ui.ui.popular

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
import com.example.mymvdb.databinding.FragmentPopularBinding
import com.example.mymvdb.utility.NetworkState

class PopularFragment : Fragment() {

    lateinit var popularViewModel: PopularViewModel
lateinit var pagedListRepository: MoviePageListRepository
   private lateinit var binding:FragmentPopularBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding=  FragmentPopularBinding.inflate( inflater,container, false)

    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularViewModel =ViewModelProvider(this).get(PopularViewModel::class.java)
        val apiService:TMDBInterface=TMDBClient.getClient()
        pagedListRepository= MoviePageListRepository(apiService)
        popularViewModel=getViewModel()
        val gridLayoutManager=GridLayoutManager(this.context,3)
        val adapter=PopularPagedListAdapter(this.context)
        gridLayoutManager.spanSizeLookup= object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType:Int=adapter.getItemViewType(position)
                if(viewType==adapter.MOVIE_VIEW_TYPE)  return 1
                else return 3
            }
        }
        binding.popularMoviesRv.apply {
            layoutManager=gridLayoutManager
            setHasFixedSize(true)
            this.adapter=adapter
        }
        popularViewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        popularViewModel.networkState.observe(viewLifecycleOwner, Observer {
            binding.progressBarPopular.visibility=if(popularViewModel.listIsEmpty() && it== NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.textErrorPopular.visibility=if (popularViewModel.listIsEmpty() && it== NetworkState.ERROR) View.VISIBLE else View.GONE
            if(!popularViewModel.listIsEmpty()){
                adapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel():PopularViewModel{
        return ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHEKED_CAST")
                return  PopularViewModel(pagedListRepository)as T
            }
        })[PopularViewModel::class.java]
    }
}
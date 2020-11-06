package com.example.mymvdb.ui.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymvdb.database.FavoriteMovieDatabase
import com.example.mymvdb.database.MovieDetails
import com.example.mymvdb.databinding.FavoritesFragmentBinding
import com.example.mymvdb.ui.ui.favorites.FavoritesFragment.Movies.Companion.movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var binding:FavoritesFragmentBinding
    private lateinit var repository: FavoritesViewModelRepository
    class Movies{
    companion object{
        lateinit var movies:LiveData<List<MovieDetails>>

    }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  FavoritesFragmentBinding.inflate( inflater,container, false)
       val favoritesDatabaseDAO=FavoriteMovieDatabase.getInstance(this.requireActivity().applicationContext).favoritesDatabaseDAO
        repository= FavoritesViewModelRepository(favoritesDatabaseDAO )
        viewModel=getViewModel()

        val gridLayoutManager= GridLayoutManager(this.context,2)
        val adapter= FavoritesListAdapter(this.requireActivity().applicationContext,viewModel)

        movies.observe(viewLifecycleOwner, {
            adapter.setmovies(it)
        })


        binding.favoritesMoviesRv.apply {
            layoutManager=gridLayoutManager
            setHasFixedSize(true)
            this.adapter=adapter
        }




        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.viewModelScope.launch(Dispatchers.IO) { movies  =repository.getAll()}
    }
    private fun getViewModel(): FavoritesViewModel {
        return ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED CAST")
                return  FavoritesViewModel(repository) as T
            }
        })[FavoritesViewModel::class.java]
    }
}
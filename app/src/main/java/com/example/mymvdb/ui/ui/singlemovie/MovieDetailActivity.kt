package com.example.mymvdb.ui.ui.singlemovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymvdb.R
import com.example.mymvdb.api.POSTER_BASE_URL
import com.example.mymvdb.api.TMDBClient
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.databinding.ActivityMovieDetailBinding
import com.example.mymvdb.movieDetail.Genre
import com.example.mymvdb.movieDetail.MovieDetails
import com.example.mymvdb.movieDetail.ProductionCompany
import com.example.mymvdb.movieDetail.ProductionCountry
import com.example.mymvdb.reposit.MovieDetailsRepository
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {


    private lateinit var binding :ActivityMovieDetailBinding
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e( "ErrorHH",binding.root.toString())
        setSupportActionBar(binding.toolbar)
       //binding.root.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val movieId :Int =intent.getIntExtra("id",1)

        val apiService:TMDBInterface=TMDBClient.getClient()
        movieDetailsRepository= MovieDetailsRepository(apiService)
        viewModel=getViewModel(movieId)
        viewModel.movieDetails.observe(this, {
            bindUI(it)
        })

    }

    @SuppressLint("SimpleDateFormat")
    private fun bindUI(it: MovieDetails?) {

       binding.toolbarLayout.isTitleEnabled=true
        binding.toolbarLayout.title=it?.original_title
        Log.d("Data",it?.original_title)
       binding.included.apply {

           taglineValueTv.text=it?.tagline
           Log.d("Data",it?.tagline)
           overviewValueTv.text=it?.overview
           Log.d("Data",it?.overview)

           genresValueTv.text=getGenres(it?.genres)
           Log.d("Data",genresValueTv.text.toString())
           statusValueTv.text=it?.status

           rBar.rating= it?.vote_average?.toFloat() ?: Float.MAX_VALUE
           val formatCurrency=NumberFormat.getCurrencyInstance(Locale.US)
           budgetValueTv.text=formatCurrency.format(it?.budget)
           revenueValueTv.text=formatCurrency.format(it?.revenue)
           releaseDateValueTv.text=it?.release_date
           runtimeValueTv.text= "${it?.runtime} ${this.root.context.getString(R.string.minutes)}"
           originalLanguageValueTv.text= it?.original_language

           productionCompaniesValueTv.text=getProductionCompanies(it?.production_companies)
           productionCountriesValueTv.text=getProductionCountries(it?.production_countries)
       }
        val posterPathUrl :String= POSTER_BASE_URL+ it?.poster_path
        Glide.with(this).load(posterPathUrl).into(binding.posterImage)
    }
fun   getProductionCompanies(list: List<ProductionCompany>?):String{
 var out:String=""
    for (d in list!!){
      out+= "${d.name} , "
  }
    return out
}
    fun   getProductionCountries(list: List<ProductionCountry>?):String{
        var out:String=""
        for (d in list!!){
            out+= "${d.name} , "
        }
        return out
    }fun   getGenres(list: List<Genre>?):String{
        var out:String=""
        for (d in list!!){
            out+= "${d.name} , "
        }
        return out
    }
    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
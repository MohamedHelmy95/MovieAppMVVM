package com.example.mymvdb.ui.ui.singlemovie

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.mymvdb.database.FavoriteMovieDatabase
import com.example.mymvdb.databinding.ActivityMovieDetailBinding
import com.example.mymvdb.movieDetail.Genre
import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.movieDetail.ProductionCompany
import com.example.mymvdb.movieDetail.ProductionCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity() : AppCompatActivity() {

private lateinit var context:Context
    private lateinit var binding :ActivityMovieDetailBinding
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("ErrorHH", binding.root.toString())
        context=this.applicationContext
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //binding.root.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        if (intent.hasExtra("id")) {
            val movieId: Int = intent.getIntExtra("id", 1)

            val apiService: TMDBInterface = TMDBClient.getClient()
            movieDetailsRepository = MovieDetailsRepository(apiService)
            viewModel = getViewModel(movieId)
            viewModel.movie.observe(this, {
                bindUI(it)
            })
        } else {

            bindUI(intent.extras?.getSerializable("movie") as Movie?)
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun bindUI(it: Movie?) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(  Dispatchers.Main ) {
        if (FavoriteMovieDatabase.getInstance(context).favoritesDatabaseDAO.checkIfExists(it!!.id)==1)
             binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24)
        else
            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        binding.fab.setOnClickListener { view ->
            GlobalScope.launch (Dispatchers.IO ) {
                if (FavoriteMovieDatabase.getInstance(context).favoritesDatabaseDAO.checkIfExists(it!!.id) == 1) {
                    binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    FavoriteMovieDatabase.getInstance(context).favoritesDatabaseDAO.delete(it.getDatabaseMovie())
                } else {
                    FavoriteMovieDatabase.getInstance(context).favoritesDatabaseDAO.insert(it.getDatabaseMovie())
                    binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
             }
            }
            }
        }
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
            voteAverageValueTv.text= "${it?.vote_average.toString()} /10"
             rBar.rating= it?.vote_average?.toFloat()!!.div(2)
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
  out=  out.removeSuffix(" , ")
    return out
}
    fun   getProductionCountries(list: List<ProductionCountry>?):String{
        var out:String=""
        for (d in list!!){
            out+= "${d.name} , "

        }
      out=  out.removeSuffix(" , ")
        return out
    }fun   getGenres(list: List<Genre>?):String{
        var out:String=""
        for (d in list!!){
                out+= "${d.name} , "
         }
       out= out.removeSuffix(" , ")
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
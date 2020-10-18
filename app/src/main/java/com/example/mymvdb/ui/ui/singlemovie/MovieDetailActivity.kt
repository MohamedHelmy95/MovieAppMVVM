package com.example.mymvdb.ui.ui.singlemovie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymvdb.R
import com.example.mymvdb.api.POSTER_BASE_URL
import com.example.mymvdb.api.TMDBClient
import com.example.mymvdb.api.TMDBInterface
import com.example.mymvdb.movieDetail.MovieDetails
import com.example.mymvdb.reposit.MovieDetailsRepository
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {



    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
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
        toolbar_layout.isTitleEnabled=true
        toolbar_layout.title=it?.original_title
        taglineValueTv.text=it?.tagline
        overviewValueTv.text=it?.overview
        var genrs:String  =genresValueTv.text.toString()
        while (it?.genres?.iterator()?.hasNext()!!){
            genrs+=it.genres.iterator().next().toString()
        }
        statusValueTv.text=it.status
        rBar.rating= it.vote_average.toFloat()
        val formatCurrency=NumberFormat.getCurrencyInstance(Locale.US)
        budgetValueTv.text=formatCurrency.format(it.budget)

        val simpleDateFormat = SimpleDateFormat("mmm-dd-yyyy")

        releaseDateValueTv.text=simpleDateFormat.format(it.release_date)
        runtimeValueTv.text= "${it.runtime}${this.getString(R.string.minutes)}"
        original_languageValueTv.text=it.original_language
        var pcomps:String  =production_companiesValueTv.text.toString()
        while (it.production_companies.iterator().hasNext()){
            pcomps+=it.production_companies.iterator().next().name
        }
        production_companiesValueTv.text=pcomps
        var pcontrs:String  =production_countriesValueTv.text.toString()
        while (it.production_countries.iterator().hasNext()){
            pcontrs+=it.production_countries.iterator().next().name
        }
        production_countriesValueTv.text=pcontrs
        val posterPathUrl :String= POSTER_BASE_URL+it.poster_path
        Glide.with(this).load(posterPathUrl).into(posterImage)
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
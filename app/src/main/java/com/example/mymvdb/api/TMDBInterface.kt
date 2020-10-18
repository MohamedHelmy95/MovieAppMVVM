package com.example.mymvdb.api

import com.example.mymvdb.movieDetail.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBInterface {
    //https://api.themoviedb.org/3/
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id : Int):Single<MovieDetails>


}
package com.example.mymvdb.api

import com.example.mymvdb.movieDetail.MovieDetails
import com.example.mymvdb.movieDetail.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBInterface {
    //https://api.themoviedb.org/3/
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id : Int):Single<MovieDetails>
@GET("movie/popular")
fun getPopularMovies(@Query("page")page:Int):Single<MovieResponse>

}
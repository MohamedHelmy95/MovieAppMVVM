package com.example.mymvdb.api

import com.example.mymvdb.movieDetail.Movie
import com.example.mymvdb.movieDetail.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBInterface {
    //https://api.themoviedb.org/3/
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id : Int):Single<Movie>
@GET("movie/popular")
fun getPopularMovies(@Query("page")page:Int):Single<MovieResponse>
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page")page:Int):Single<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page")page:Int):Single<MovieResponse>

}
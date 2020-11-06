package com.example.mymvdb.movieDetail


import com.example.mymvdb.database.MovieDetails

import java.io.Serializable


data class Movie(

        val adult: Boolean,
        val backdrop_path: String,
        val budget: Int,
        val genres: List<Genre>,
        val homepage: String,
        val id: Int,
        val imdb_id: String,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val production_companies: List<ProductionCompany>,
        val production_countries: List<ProductionCountry>,
        val release_date: String,
        val revenue: Float,
        val runtime: Int,
        val spoken_languages: List<SpokenLanguage>,
        val status: String,
        val tagline: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int

) : Serializable{
        fun getDatabaseMovie():MovieDetails{
                return MovieDetails(adult,backdrop_path,budget,genres, homepage, id, imdb_id, original_language, original_title, overview, popularity, poster_path, production_companies, production_countries, release_date, revenue, runtime, spoken_languages, status, tagline, title, video, vote_average, vote_count )

        }
}



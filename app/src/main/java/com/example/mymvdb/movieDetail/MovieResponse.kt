package com.example.mymvdb.movieDetail

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MovieResponse(
    val page: Int,
    @SerializedName("results")
    val moviesList: List<Movie>,
    val total_pages: Int,
    val total_results: Int
):Serializable
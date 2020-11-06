package com.example.mymvdb.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymvdb.movieDetail.Genre
import com.example.mymvdb.movieDetail.ProductionCompany
import com.example.mymvdb.movieDetail.ProductionCountry
import com.example.mymvdb.movieDetail.SpokenLanguage
import java.io.Serializable

@Entity(tableName = "favorites_table")
data class MovieDetails(
    @ColumnInfo(name ="adult")
    val adult: Boolean,
    @ColumnInfo(name ="backdrop_path")
    val backdrop_path: String,
    @ColumnInfo(name ="budget")
    val budget: Int,
    @ColumnInfo(name ="genres")
    val genres: List<Genre>,
    @ColumnInfo(name ="homepage")
    val homepage: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movieId")
    val id: Int,
    @ColumnInfo(name ="imdb_id")
    val imdb_id: String,
    @ColumnInfo(name ="original_language")
    val original_language: String,
    @ColumnInfo(name ="original_title")
    val original_title: String,
    @ColumnInfo(name ="overview")
    val overview: String,
    @ColumnInfo(name ="popularity")
    val popularity: Double,
    @ColumnInfo(name ="poster_path")
    val poster_path: String,
    @ColumnInfo(name ="production_companies")
    val production_companies: List<ProductionCompany>,
    @ColumnInfo(name ="production_countries")
    val production_countries: List<ProductionCountry>,
    @ColumnInfo(name ="release_date")
    val release_date: String,
    @ColumnInfo(name ="revenue")
    val revenue: Float,
    @ColumnInfo(name ="runtime")
    val runtime: Int,
    @ColumnInfo(name ="spoken_languages")
    val spoken_languages: List<SpokenLanguage>,
    @ColumnInfo(name ="status")
    val status: String,
    @ColumnInfo(name ="tagline")
    val tagline: String,
    @ColumnInfo(name ="title")
    val title: String,
    @ColumnInfo(name ="video")
    val video: Boolean,
    @ColumnInfo(name ="vote_average")
    val vote_average: Double,
    @ColumnInfo(name ="vote_count")
    val vote_count: Int
):Serializable
package com.example.mymvdb.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavoritesDatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend   fun insert(movie: MovieDetails)
@Delete
suspend fun delete(movie: MovieDetails)
@Query("SELECT * from favorites_table ")
 fun getAll(): LiveData<List<MovieDetails>>
    @Query("SELECT EXISTS(SELECT 1 FROM favorites_table WHERE movieId=:id LIMIT 1)")
  suspend  fun checkIfExists(id:Int):Int



}
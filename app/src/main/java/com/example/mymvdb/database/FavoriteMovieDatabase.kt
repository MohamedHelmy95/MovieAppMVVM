package com.example.mymvdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [MovieDetails::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class FavoriteMovieDatabase :RoomDatabase() {
    abstract val favoritesDatabaseDAO:FavoritesDatabaseDAO
    companion object{
       @Volatile
       private var  INSTANCE:FavoriteMovieDatabase?=null
        fun getInstance(context: Context):FavoriteMovieDatabase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance=Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteMovieDatabase::class.java,
                        "favorite_movie_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE=instance
                }
                return instance
            }
        }

    }

}
package com.michelbarbosa.nytmovies.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.michelbarbosa.nytmovies.data.dao.movie.MovieDao
import com.michelbarbosa.nytmovies.data.dao.movie.Movie


@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao() : MovieDao

    companion object{
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this){
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "nytMovies_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE!!
        }
    }

}
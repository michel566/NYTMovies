package com.michelbarbosa.nytmovies.data.dao.movie

import android.content.Context
import androidx.lifecycle.LiveData
import com.michelbarbosa.nytmovies.data.dao.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MovieRepository {

    companion object{
        var appDatabase : AppDatabase? = null
        var movieLDList : LiveData<List<Movie>>? = null

        fun initializeDB(context: Context) : AppDatabase{
            return AppDatabase.getDatabase(context)
        }

        fun insertMovieList(context: Context, movieList: List<Movie>){
            appDatabase = initializeDB(context = context)

            CoroutineScope(IO).launch {
                appDatabase!!.movieDao().InsertMovieList(movieList)
            }
        }

        fun getAllMovies(context: Context) : LiveData<List<Movie>>?{
            appDatabase = initializeDB(context)
            movieLDList = appDatabase!!.movieDao().getAllMovies()
            return movieLDList
        }

    }

}
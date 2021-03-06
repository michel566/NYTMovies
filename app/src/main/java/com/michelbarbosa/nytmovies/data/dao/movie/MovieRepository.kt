package com.michelbarbosa.nytmovies.data.dao.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michelbarbosa.nytmovies.data.dao.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MovieRepository {

    companion object{
        var appDatabase : AppDatabase? = null
        var movieLDList : LiveData<List<Movie>>? = null
        var movieResultList: MutableLiveData<List<Movie>> = MutableLiveData()

        fun initializeDB(context: Context) : AppDatabase{
            return AppDatabase.getDatabase(context)
        }

        fun insertMovieList(context: Context, movieList: List<Movie>){
            appDatabase = initializeDB(context = context)
            CoroutineScope(IO).launch {
                appDatabase!!.movieDao().InsertMovieList(movieList)
            }
        }

        fun updateSetFavorite(context: Context, idMovie: Int, isFavorite: Boolean){
            appDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                appDatabase!!.movieDao().UpdateSetFavorite(idMovie, isFavorite)
            }
        }

        fun getAllMovies(context: Context) : LiveData<List<Movie>>?{
            appDatabase = initializeDB(context)
            movieLDList = appDatabase!!.movieDao().getAllMovies()
            return movieLDList
        }

        fun getResult(context: Context): MutableLiveData<List<Movie>>?{
            appDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                movieResultList.postValue(appDatabase!!.movieDao().getResult())
            }
            return movieResultList
        }

    }

}
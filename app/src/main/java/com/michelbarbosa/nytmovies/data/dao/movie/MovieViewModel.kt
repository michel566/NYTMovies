package com.michelbarbosa.nytmovies.data.dao.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {

    var movieLDList: LiveData<List<Movie>>? = null

    fun insertMovieList(context: Context, movieList: List<Movie>){
       MovieRepository.insertMovieList(context, movieList)
    }

    fun getAllMovies(context: Context): LiveData<List<Movie>>?{
        movieLDList = MovieRepository.getAllMovies(context)
        return movieLDList
    }

}
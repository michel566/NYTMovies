package com.michelbarbosa.nytmovies.data.dao.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {

    var movieLDList: LiveData<List<Movie>>? = null

    fun insertMovieList(context: Context, currentList: List<Movie>?, newList: List<Movie>){
        var innerJoinList : MutableList<Movie>? = ArrayList<Movie>()
        if (currentList != null) {
            for (movie in newList){
                if(!currentList.contains(movie)){
                    innerJoinList?.add(movie)
                }
            }
            if (innerJoinList != null) {
                MovieRepository.insertMovieList(context, innerJoinList)
            }
        } else{
            MovieRepository.insertMovieList(context, newList)
        }

    }

    fun getAllMovies(context: Context): LiveData<List<Movie>>?{
        movieLDList = MovieRepository.getAllMovies(context)
        return movieLDList
    }

}
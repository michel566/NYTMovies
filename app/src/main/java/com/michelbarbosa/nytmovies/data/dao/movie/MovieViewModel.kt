package com.michelbarbosa.nytmovies.data.dao.movie

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {

    var movieLDList: LiveData<List<Movie>>? = null
    var movieResultList: MutableLiveData<List<Movie>>? = null

    fun insertMovieList(context: Context, currentList: List<Movie>?, newList: List<Movie>) {
        var innerJoinList: MutableList<Movie>? = ArrayList<Movie>()
        if (currentList != newList) {
            if (currentList != null) {
                for (movie in newList) {
                    if (!currentList.contains(movie)) {
                        innerJoinList?.add(movie)
                    }
                }
                if (innerJoinList != null) {
                    MovieRepository.insertMovieList(context, innerJoinList)
                }
            } else {
                MovieRepository.insertMovieList(context, newList)
            }
        }

    }

    fun updateSetFavorite(context: Context, idMovie: Int, isFavorite: Boolean){
        return MovieRepository.updateSetFavorite(context, idMovie, isFavorite)
    }

    fun getAllMovies(context: Context): LiveData<List<Movie>>? {
        movieLDList = MovieRepository.getAllMovies(context)
        return movieLDList
    }

    fun getResult(context: Context): MutableLiveData<List<Movie>>? {
        movieResultList = MovieRepository.getResult(context)
        return movieResultList
    }

}
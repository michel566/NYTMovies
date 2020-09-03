package com.michelbarbosa.nytmovies.presenter

import android.content.Context
import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.michelbarbosa.nytmovies.enums.ErrorType
import michel566.androidmodules.lightdialog.DialogType

interface NYTMoviesContract {

    interface ShowMoviesView {
        fun loadAllMovies(movies: List<Movie>)
        fun showError(errorType: ErrorType, dialogType: DialogType)
    }

    interface NYTMoviesPresenter{
        fun getAllMovies(context: Context, query: String, pageNumber: Int)
    }

}
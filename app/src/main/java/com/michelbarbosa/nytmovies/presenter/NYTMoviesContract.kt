package com.michelbarbosa.nytmovies.presenter

import android.content.Context
import com.michelbarbosa.nytmovies.enums.ErrorType
import com.michelbarbosa.nytmovies.model.Movie
import michel566.androidmodules.lightdialog.DialogType

interface NYTMoviesContract {

    interface ShowMoviesView {
        fun showMovies(movies: List<Movie>)
        fun showError(errorType: ErrorType, dialogType: DialogType)
    }

    interface NYTMoviesPresenter{
        fun getAllMovies(context: Context, query: String, numOfPages: Int)
    }

}
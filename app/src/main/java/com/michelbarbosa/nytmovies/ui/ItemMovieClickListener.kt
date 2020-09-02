package com.michelbarbosa.nytmovies.ui

import com.michelbarbosa.nytmovies.model.Movie

interface ItemMovieClickListener {
    fun onClickMovie(movie: Movie)
}
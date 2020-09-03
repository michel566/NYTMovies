package com.michelbarbosa.nytmovies.ui

import com.michelbarbosa.nytmovies.data.dao.movie.Movie

interface ItemMovieClickListener {
    fun onClickMovie(movie: Movie)
}
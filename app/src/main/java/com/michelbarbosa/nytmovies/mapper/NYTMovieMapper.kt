package com.michelbarbosa.nytmovies.mapper

import com.michelbarbosa.nytmovies.data.dao.movie.Movie
import com.michelbarbosa.nytmovies.network.response.MovieResult
import com.michelbarbosa.nytmovies.network.response.NYTMoviesResponse
import java.util.*

object NYTMovieMapper {

    fun movieMapper(response: NYTMoviesResponse?): List<Movie> {
        val movieList: MutableList<Movie> =
            ArrayList()
        if (response != null) {
            val results: List<MovieResult> = response.results
            for (result in results) {
                val movie =
                    Movie(
                        result.display_title,
                        result.mpaa_rating,
                        result.headline,
                        result.summary_short,
                        result.publication_date,
                        result.date_updated,
                        result.link.suggested_link_text,
                        result.link.url,
                        result.multimedia.src!!
                    )
                movieList.add(movie)
            }
        }
        return movieList
    }

}
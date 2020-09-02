package com.michelbarbosa.nytmovies.network.response

class NYTMoviesResponse(val status: String, val copyright: String, val has_more: Boolean,
                        val num_results: Int, val results : List<MovieResult>)
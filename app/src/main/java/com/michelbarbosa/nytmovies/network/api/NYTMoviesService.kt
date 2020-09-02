package com.michelbarbosa.nytmovies.network.api

import com.michelbarbosa.nytmovies.network.response.NYTMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTMoviesService{

    @GET("reviews/all.json")
    fun getAllReviewMovies(@Query("query") query: String,
                                  @Query("api-key") apikey: String,
                                  @Query("offset") offset: String) : Call<NYTMoviesResponse>

}